package tourapi24.backend.relationship.memberlikeplace;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.place.domain.Place;
import tourapi24.backend.place.repository.PlaceRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberLikePlaceService {

    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final MemberLikePlaceRepository memberLikePlaceRepository;

    @Transactional
    public void likePlace(Long memberId, Long placeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NoSuchElementException("해당 장소를 찾을 수 없습니다."));

        if (memberLikePlaceRepository.existsByMemberAndPlace(member, place)) {
            throw new IllegalStateException("이미 좋아요를 누른 장소입니다.");
        }

        MemberLikePlace memberLikePlace = MemberLikePlace.builder()
                .member(member)
                .place(place)
                .build();
        memberLikePlaceRepository.save(memberLikePlace);

        place.updateLikeCount(memberLikePlaceRepository.countByPlace(place));
        placeRepository.save(place);
    }

    @Transactional
    public void unlikePlace(Long memberId, Long placeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NoSuchElementException("해당 장소를 찾을 수 없습니다."));

        memberLikePlaceRepository.deleteByMemberAndPlace(member, place);

        place.updateLikeCount(memberLikePlaceRepository.countByPlace(place));
        placeRepository.save(place);
    }
    
    public List<Place> getLikedPlaces(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));

        List<MemberLikePlace> likes = memberLikePlaceRepository.findAllByMember(member);
        return likes.stream()
                .map(MemberLikePlace::getPlace)
                .collect(Collectors.toList());
    }
}
