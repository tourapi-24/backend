package tourapi24.backend.travellog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tourapi24.backend.travellog.domain.SentenceOpinion;
import tourapi24.backend.travellog.domain.TravelLog;

import java.util.List;

public interface TravelLogRepository extends JpaRepository<TravelLog, Long> {
    @Query("select t from TravelLog t order by t.date desc limit 25")
    List<TravelLog> findRecentTravelLogs();

    @Query("select t from TravelLog t where t.title like %:query% order by t.date desc")
    List<TravelLog> findTravelLogsByQuery(String query);

    @Query("SELECT t.sentenceOpinions FROM TravelLog t WHERE t.place.id = :placeId")
    List<List<SentenceOpinion>> findSentenceOpinionsByPlaceId(@Param("placeId") Long placeId);

    @Query("SELECT t.emojiOpinion, COUNT(t) FROM TravelLog t WHERE t.place.id = :placeId GROUP BY t.emojiOpinion")
    List<Object[]> countEmojiOpinionsByPlaceId(@Param("placeId") Long placeId);
}
