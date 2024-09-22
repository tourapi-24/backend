package tourapi24.backend.relationship.memberlikeplace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.place.domain.Place;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberLikePlaceRepository extends JpaRepository<MemberLikePlace, Long> {

    boolean existsByMemberAndPlace(Member member, Place place);

    Optional<MemberLikePlace> findByMemberAndPlace(Member member, Place place);

    List<MemberLikePlace> findAllByMember(Member member);

    List<MemberLikePlace> findAllByPlace(Place place);

    long countByPlace(Place place);

    void deleteByMemberAndPlace(Member member, Place place);
}
