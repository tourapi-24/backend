package tourapi24.backend.place.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tourapi24.backend.travellog.domain.TravelLog;

import java.util.ArrayList;
import java.util.List;

//@ToString //TODO DEBUG
@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long contentId; // tourapi ID

    @NotNull
    private Long kakaoId; // kakao map id

    @NotNull
    @Enumerated(EnumType.STRING)
    private GovContentType contentType;

    @NotNull
    private String title;

    private String tel;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BusanGu busanGu;

    @ElementCollection
    @CollectionTable(name = "place_images", joinColumns = @JoinColumn(name = "place_id"))
    @Column(name = "image")
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private List<TravelLog> travelLogs;

    @Builder.Default
    private Long likeCount = 0L;

    @NotNull
    private Boolean isCongestion;
    // Congestion Data
    private Float congestion_00;
    private Float congestion_01;
    private Float congestion_02;
    private Float congestion_03;
    private Float congestion_04;
    private Float congestion_05;
    private Float congestion_06;
    private Float congestion_07;
    private Float congestion_08;
    private Float congestion_09;
    private Float congestion_10;
    private Float congestion_11;
    private Float congestion_12;
    private Float congestion_13;
    private Float congestion_14;
    private Float congestion_15;
    private Float congestion_16;
    private Float congestion_17;
    private Float congestion_18;
    private Float congestion_19;
    private Float congestion_20;
    private Float congestion_21;
    private Float congestion_22;
    private Float congestion_23;

    public void addImage(String url) {
        this.images.add(url);
    }

    public void updateLikeCount(Long count) {
        this.likeCount = count;
    }

    public void setCongestionData(Integer max, List<Integer> avg) {
        this.congestion_00 = (float) avg.get(0) / max;
        this.congestion_01 = (float) avg.get(1) / max;
        this.congestion_02 = (float) avg.get(2) / max;
        this.congestion_03 = (float) avg.get(3) / max;
        this.congestion_04 = (float) avg.get(4) / max;
        this.congestion_05 = (float) avg.get(5) / max;
        this.congestion_06 = (float) avg.get(6) / max;
        this.congestion_07 = (float) avg.get(7) / max;
        this.congestion_08 = (float) avg.get(8) / max;
        this.congestion_09 = (float) avg.get(9) / max;
        this.congestion_10 = (float) avg.get(10) / max;
        this.congestion_11 = (float) avg.get(11) / max;
        this.congestion_12 = (float) avg.get(12) / max;
        this.congestion_13 = (float) avg.get(13) / max;
        this.congestion_14 = (float) avg.get(14) / max;
        this.congestion_15 = (float) avg.get(15) / max;
        this.congestion_16 = (float) avg.get(16) / max;
        this.congestion_17 = (float) avg.get(17) / max;
        this.congestion_18 = (float) avg.get(18) / max;
        this.congestion_19 = (float) avg.get(19) / max;
        this.congestion_20 = (float) avg.get(20) / max;
        this.congestion_21 = (float) avg.get(21) / max;
        this.congestion_22 = (float) avg.get(22) / max;
        this.congestion_23 = (float) avg.get(23) / max;
    }


}
