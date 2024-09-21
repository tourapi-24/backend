package tourapi24.backend.place.dto.external.gov;

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
public class AreaBasedListResponse {
    @JsonProperty("response")
    private Response response;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
//        @JsonProperty("header")
//        private Header header;

        @JsonProperty("body")
        private Body body;
    }

//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class Header {
//        @JsonProperty("resultCode")
//        private String resultCode;
//
//        @JsonProperty("resultMsg")
//        private String resultMsg;
//    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        @JsonProperty("items")
        private Items items;

//        @JsonProperty("numOfRows")
//        private int numOfRows;
//
//        @JsonProperty("pageNo")
//        private int pageNo;
//
//        @JsonProperty("totalCount")
//        private int totalCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Items {
        @JsonProperty("item")
        private List<Item> item;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        @JsonProperty("addr1")
        private String addr1;

//        @JsonProperty("addr2")
//        private String addr2;

//        @JsonProperty("areacode")
//        private String areacode;

//        @JsonProperty("booktour")
//        private String booktour;

        @JsonProperty("cat1")
        private String cat1;

        @JsonProperty("cat2")
        private String cat2;

        @JsonProperty("cat3")
        private String cat3;

        @JsonProperty("contentid")
        private String contentid;

        @JsonProperty("contenttypeid")
        private String contenttypeid;

//        @JsonProperty("createdtime")
//        private String createdtime;

        @JsonProperty("firstimage")
        private String firstimage;

        @JsonProperty("firstimage2")
        private String firstimage2;

//        @JsonProperty("cpyrhtDivCd")
//        private String cpyrhtDivCd;

        @JsonProperty("mapx")
        private String mapx;

        @JsonProperty("mapy")
        private String mapy;

//        @JsonProperty("mlevel")
//        private String mlevel;

//        @JsonProperty("modifiedtime")
//        private String modifiedtime;

        @JsonProperty("sigungucode")
        private String sigungucode;

//        @JsonProperty("tel")
//        private String tel;

        @JsonProperty("title")
        private String title;

//        @JsonProperty("zipcode")
//        private String zipcode;
    }
}
