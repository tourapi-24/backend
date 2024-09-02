package tourapi24.backend.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TouristSpot {
    private double distance;
    private String title;
    private Double latitude;
    private Double longitude;
}
