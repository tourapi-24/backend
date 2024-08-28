package tourapi24.backend.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tourapi24.backend.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Modifying
    @Query("update Member m set m.username = :username where m.id = :id")
    int updateUsernameById(Long id, String username);

    @Modifying
    @Query("update Member m set m.bio = :bio where m.id = :id")
    int updateBioById(Long id, String bio);

    Optional<Member> findOneBySocialId(String socialId);
}
