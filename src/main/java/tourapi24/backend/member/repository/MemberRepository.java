package tourapi24.backend.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tourapi24.backend.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findOneBySocialId(String socialId);
}
