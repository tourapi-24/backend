package tourapi24.backend.gaongi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourapi24.backend.gaongi.domain.Gaongi;

@Repository
public interface GaongiRepository extends JpaRepository<Gaongi, Long> {
    Gaongi findByMemberId(Long memberId);
}
