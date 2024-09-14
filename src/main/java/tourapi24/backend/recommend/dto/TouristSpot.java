package tourapi24.backend.recommend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Table(name = "tourist_spot")
public class TouristSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double distance;
    private String title;
    private Double latitude;
    private Double longitude;
    private String cat1;
    private String cat2;
    private String cat3;
    private String firstImage;


    public TouristSpot(Double distance, String title, Double latitude, Double longitude, String cat1, String cat2, String cat3, String firstImage) {
        this.distance = distance;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.firstImage = firstImage;
    }

}