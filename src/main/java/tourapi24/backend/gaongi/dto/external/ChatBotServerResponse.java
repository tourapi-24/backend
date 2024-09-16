package tourapi24.backend.gaongi.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatBotServerResponse {
    @JsonProperty("answer")
    private String answer;
}
