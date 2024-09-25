package tourapi24.backend.gaongi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GaongiResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("level")
    private Integer level;

    @JsonProperty("exp")
    private Integer exp;
}
