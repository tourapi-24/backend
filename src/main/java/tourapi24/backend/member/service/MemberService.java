package tourapi24.backend.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tourapi24.backend.gaongi.domain.Gaongi;
import tourapi24.backend.gaongi.repository.GaongiRepository;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.dto.profile.ProfileResponse;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.place.domain.Place;
import tourapi24.backend.relationship.memberlikeplace.MemberLikePlaceService;
import tourapi24.backend.relationship.memberliketravellog.MemberLikeTravelLogService;
import tourapi24.backend.travellog.domain.TravelLog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final GaongiRepository gaongiRepository;
    private final MemberLikeTravelLogService memberLikeTravelLogService;
    private final MemberLikePlaceService memberLikePlaceService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ProfileResponse getProfile(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        Gaongi gaongi = gaongiRepository.findByMemberId(id);
        List<TravelLog> travelLogs = memberLikeTravelLogService.getLikedTravelLogs(member.getId());
        List<Place> places = memberLikePlaceService.getLikedPlaces(member.getId());

        return ProfileResponse.builder()
                .username(member.getUsername())
                .profileImage(member.getProfileImage())
                .bio(member.getBio())
                .travelLogCount(travelLogs.size())
                .gaongiLevel(gaongi.getLevel())
                .travelLogs(travelLogs.stream().map(t -> ProfileResponse.TravelLogDto.builder()
                                .travelLogTitle(t.getTitle())
                                .travelLogImage(t.getMedia().getFirst())
                                .placeTitle(t.getPlace().getTitle())
                                .likeCount(t.getLikeCount())
                                .build())
                        .toList())
                .places(places.stream().map(p -> ProfileResponse.PlaceDto.builder()
                                .placeTitle(p.getTitle())
                                .placeImage(p.getImages().getFirst())
                                .build())
                        .toList())
                .build();
    }

    @Transactional
    public int updateBio(Long id, String bio) {
        return memberRepository.updateBioById(id, bio);
    }

    @Transactional
    public String updateProfile(Long id, MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath);

        memberRepository.updateProfileImageById(id, fileName);
        return fileName;
    }
}
