package tourapi24.backend.travellog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import tourapi24.backend.place.domain.GovContentType;
import tourapi24.backend.travellog.domain.CongestionLevel;
import tourapi24.backend.travellog.domain.SentenceOpinion;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TravelLogResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("place_id")
    private Long placeId;

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("place_content_type")
    private GovContentType placeContentType;

    @JsonProperty("place_address")
    private String placeAddress;

    @JsonProperty("title")
    private String title;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("emoji_opinion")
    private String emojiOpinion;

    @JsonProperty("congestion_level")
    private CongestionLevel congestionLevel;

    @JsonProperty("sentence_opinion")
    private List<SentenceOpinion> sentenceOpinions;

    @JsonProperty("visit_together")
    private List<Long> visitTogether;

    @JsonProperty("media")
    private List<String> media;

    @JsonProperty("content")
    private String content;
}
