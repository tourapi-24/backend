package tourapi24.backend.place.dto.external.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoCoordResponse {
    private Meta meta;
    private List<Document> documents;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Meta {
        @JsonProperty("total_count")
        private int totalCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Document {
        @JsonProperty("region_type")
        private String regionType;

        private String code;

        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("region_1depth_name")
        private String region1depthName;

        @JsonProperty("region_2depth_name")
        private String region2depthName;

        @JsonProperty("region_3depth_name")
        private String region3depthName;

        @JsonProperty("region_4depth_name")
        private String region4depthName;

        private double x;
        private double y;
    }
}
