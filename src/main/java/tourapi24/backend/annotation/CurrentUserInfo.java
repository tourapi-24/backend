package tourapi24.backend.annotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CurrentUserInfo {
    private final Long userId;
    private final String username;
}
