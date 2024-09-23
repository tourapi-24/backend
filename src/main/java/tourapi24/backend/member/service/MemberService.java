package tourapi24.backend.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tourapi24.backend.gaongi.repository.GaongiRepository;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.place.repository.PlaceRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final GaongiRepository gaongiRepository;
    private final PlaceRepository placeRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

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
