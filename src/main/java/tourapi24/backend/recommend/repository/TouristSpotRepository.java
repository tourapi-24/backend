package tourapi24.backend.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tourapi24.backend.recommend.dto.TouristSpot;

public interface TouristSpotRepository extends JpaRepository<TouristSpot, Long> {
}