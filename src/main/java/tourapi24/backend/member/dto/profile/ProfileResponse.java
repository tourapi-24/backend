package tourapi24.backend.member.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tourapi24.backend.place.domain.GovContentType;

import java.util.List;

@Getter
@Builder
public class ProfileResponse {

    @JsonProperty("username")
    private final String username;

    @JsonProperty("profile_image")
    private final String profileImage;

    @JsonProperty("bio")
    private final String bio;

    @JsonProperty("travel_log_count")
    private final int travelLogCount;

    @JsonProperty("gaongi_level")
    private final int gaongiLevel;

    @JsonProperty("travel_logs")
    private final List<TravelLogDto> travelLogs;

    @JsonProperty("places")
    private final List<PlaceDto> places;

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class TravelLogDto {

        @JsonProperty("travel_log_title")
        private String travelLogTitle;

        @JsonProperty("travel_log_image")
        private String travelLogImage;

        @JsonProperty("place_title")
        private String placeTitle;

        @JsonProperty("like_count")
        private long likeCount;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class PlaceDto {
        @JsonProperty("place_title")
        private String placeTitle;

        @JsonProperty("place_image")
        private String placeImage;

        @JsonProperty("content_type")
        private GovContentType contentType;

        @JsonProperty("congestion_level")
        private int congestionLevel;
    }

}
