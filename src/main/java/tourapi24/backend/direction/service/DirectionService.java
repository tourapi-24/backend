package tourapi24.backend.direction.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectionService {

    @Value("${api.google}")
    private String api;

    @Autowired
    RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    public ResponseEntity<?> getRoute(String origin, String destination) {

        try {
            String url = String.format("%s&origin=%s&destination=%s",
                    api, origin, destination);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode routes = root.path("routes");

                if (routes.isArray() && !routes.isEmpty()) {
                    JsonNode firstRoute = routes.get(0);
                    JsonNode legs = firstRoute.path("legs");

                    if (legs.isArray()) {
                        List<JsonNode> legsList = new ArrayList<>();
                        legs.forEach(legsList::add);
                        return ResponseEntity.ok(legsList);
                    }
                }

                return ResponseEntity.badRequest().body("No valid legs found in the response");
            } else {
                return ResponseEntity.status(response.getStatusCode())
                        .body("Error from Google Maps API: " + response.getBody());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing the request: " + e.getMessage());
        }

    }
}
