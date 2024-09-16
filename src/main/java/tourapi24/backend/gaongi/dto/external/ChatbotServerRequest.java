package tourapi24.backend.gaongi.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ChatbotServerRequest {
    @JsonProperty("query")
    private String query;

    @JsonProperty("name")
    private String name;

    @JsonProperty("level")
    private Integer level;

    @JsonProperty("key")
    private String key;
}
