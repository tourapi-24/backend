package tourapi24.backend.travellog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TravelLogCreateResponse {

    @JsonProperty("travel_log_id")
    private Long travelLogId;
}
