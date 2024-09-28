package tourapi24.backend.place.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import tourapi24.backend.place.domain.GovContentType;
import tourapi24.backend.travellog.domain.EmojiOpinion;
import tourapi24.backend.travellog.domain.SentenceOpinion;

import java.util.List;

@Getter
@Builder
public class PlaceDetailResponse {

    @JsonProperty("title")
    private String title;

    @JsonProperty("content_type")
    private GovContentType contentType;

    @JsonProperty("image_urls")
    private List<String> imageUrls;

    @JsonProperty("congestion_level")
    private Integer congestionLevel;

    @JsonProperty("address")
    private String address;

    @JsonProperty("sentence_opinions")
    private List<SentenceOpinion> sentenceOpinions;

    @JsonProperty("emoji_opinions")
    private List<kEmojiOpinion> emojiOpinions;

    @Getter
    @Builder
    public static class kEmojiOpinion {
        @JsonProperty("emoji")
        private EmojiOpinion opinion;

        @JsonProperty("count")
        private Long count;
    }
}
