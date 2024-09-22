package tourapi24.backend.travellog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tourapi24.backend.travellog.domain.TravelLog;

public interface TravelLogRepository extends JpaRepository<TravelLog, Long> {
}
