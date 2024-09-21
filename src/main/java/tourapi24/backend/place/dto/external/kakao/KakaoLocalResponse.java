package tourapi24.backend.place.dto.external.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoLocalResponse {

    @JsonProperty("documents")
    private List<Document> documents;

//    @JsonProperty("meta")
//    private Meta meta;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {
        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("category_group_code")
        private String categoryGroupCode;

        @JsonProperty("category_group_name")
        private String categoryGroupName;

        @JsonProperty("category_name")
        private String categoryName;

        @JsonProperty("distance")
        private String distance;

        @JsonProperty("id")
        private String id;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("place_url")
        private String placeUrl;

        @JsonProperty("road_address_name")
        private String roadAddressName;

        @JsonProperty("x")
        private String x;

        @JsonProperty("y")
        private String y;
    }

//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class Meta {
//        @JsonProperty("is_end")
//        private boolean isEnd;
//
//        @JsonProperty("pageable_count")
//        private int pageableCount;
//
//        @JsonProperty("same_name")
//        private SameName sameName;
//
//        @JsonProperty("total_count")
//        private int totalCount;
//    }

//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class SameName {
//        @JsonProperty("keyword")
//        private String keyword;
//
//        @JsonProperty("region")
//        private List<String> region;
//
//        @JsonProperty("selected_region")
//        private String selectedRegion;
//    }
}
