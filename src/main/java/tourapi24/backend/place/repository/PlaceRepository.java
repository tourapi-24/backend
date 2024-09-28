package tourapi24.backend.place.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tourapi24.backend.place.domain.GovContentType;
import tourapi24.backend.place.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query(
            "select p from Place p " +
                    "where p.isCongestion = true  " +
                    "and (:contentType is null or p.contentType = :contentType) " +
                    "and (6371 * acos(cos(radians(:userY)) * " +
                    "cos(radians(p.y)) * cos(radians(p.x) - radians(:userX)) + " +
                    "sin(radians(:userY)) * sin(radians(p.y))) * 1000) < :radiusMeter " +
                    "order by case " +
                    "when :hour = 0 then p.congestion_00 " +
                    "when :hour = 1 then p.congestion_01 " +
                    "when :hour = 2 then p.congestion_02 " +
                    "when :hour = 3 then p.congestion_03 " +
                    "when :hour = 4 then p.congestion_04 " +
                    "when :hour = 5 then p.congestion_05 " +
                    "when :hour = 6 then p.congestion_06 " +
                    "when :hour = 7 then p.congestion_07 " +
                    "when :hour = 8 then p.congestion_08 " +
                    "when :hour = 9 then p.congestion_09 " +
                    "when :hour = 10 then p.congestion_10 " +
                    "when :hour = 11 then p.congestion_11 " +
                    "when :hour = 12 then p.congestion_12 " +
                    "when :hour = 13 then p.congestion_13 " +
                    "when :hour = 14 then p.congestion_14 " +
                    "when :hour = 15 then p.congestion_15 " +
                    "when :hour = 16 then p.congestion_16 " +
                    "when :hour = 17 then p.congestion_17 " +
                    "when :hour = 18 then p.congestion_18 " +
                    "when :hour = 19 then p.congestion_19 " +
                    "when :hour = 20 then p.congestion_20 " +
                    "when :hour = 21 then p.congestion_21 " +
                    "when :hour = 22 then p.congestion_22 " +
                    "when :hour = 23 then p.congestion_23 " +
                    "end asc"
    )
    Page<Place> findNearbyPlacesByContentId(
            @Param("userX") double userX,
            @Param("userY") double userY,
            @Param("radiusMeter") int radiusMeter,
            @Param("hour") int hour,
            @Param("contentType") GovContentType contentType,
            Pageable pageable
    );
}
