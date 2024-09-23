package tourapi24.backend.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tourapi24.backend.member.domain.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Modifying
    @Query("update Member m set m.bio = :bio where m.id = :id")
    int updateBioById(Long id, String bio);

    @Modifying
    @Query("update Member m set m.profileImage = :profileImage where m.id = :id")
    void updateProfileImageById(Long id, String profileImage);


    Optional<Member> findOneBySocialId(String socialId);
}
