package tourapi24.backend.place.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tourapi24.backend.place.domain.GovContentType;

@ToString //TODO DEBUG
@Getter
@Builder
public class PlaceRecommendationResponse {

    @JsonProperty("title")
    private String title;

    @JsonProperty("content_type")
    private GovContentType contentType;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("congestion_level")
    private Integer congestionLevel;
}
