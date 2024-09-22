package tourapi24.backend.travellog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tourapi24.backend.travellog.domain.CongestionLevel;
import tourapi24.backend.travellog.domain.EmojiOpinion;
import tourapi24.backend.travellog.domain.SentenceOpinion;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TravelLogCreateRequest {

    @JsonProperty("place_id")
    private Long placeId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("emoji_opinion")
    private EmojiOpinion emojiOpinion;

    @JsonProperty("congestion_level")
    private CongestionLevel congestionLevel;

    @JsonProperty("sentence_opinion")
    private List<SentenceOpinion> sentenceOpinion;

    @JsonProperty("visit_together")
    private List<Long> visitTogether;

    @JsonProperty("media")
    private List<String> media;

    @JsonProperty("content")
    private String content;
}

