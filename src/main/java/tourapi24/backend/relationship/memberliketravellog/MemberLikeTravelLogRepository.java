package tourapi24.backend.relationship.memberliketravellog;

import org.springframework.data.jpa.repository.JpaRepository;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.travellog.domain.TravelLog;

import java.util.List;

public interface MemberLikeTravelLogRepository extends JpaRepository<MemberLikeTravelLog, Long> {
    boolean existsByMemberAndTravelLog(Member member, TravelLog travelLog);

    long countByTravelLog(TravelLog travelLog);

    void deleteByMemberAndTravelLog(Member member, TravelLog travelLog);

    List<MemberLikeTravelLog> findAllByMember(Member member);
}
