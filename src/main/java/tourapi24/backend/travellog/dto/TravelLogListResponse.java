package tourapi24.backend.travellog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TravelLogListResponse {

    @JsonProperty("travel_logs")
    private final List<TravelLogResponse> travelLogs;
}
