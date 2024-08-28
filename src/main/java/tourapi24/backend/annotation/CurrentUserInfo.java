package tourapi24.backend.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrentUserInfo {
    private final Long userId;
    private final String username;
}
