package tourapi24.backend.place.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import tourapi24.backend.place.domain.GovContentType;

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
    private int congestionLevel;

    @JsonProperty("address")
    private String address;

    //TODO 방문일기 관련 필드 추가하기
}
