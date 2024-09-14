package tourapi24.backend.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import tourapi24.backend.recommend.dto.TouristSpot;

public interface TouristSpotRepository extends JpaRepository<TouristSpot, Long> {
}