package tourapi24.backend.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tourapi24.backend.place.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
