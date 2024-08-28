package tourapi24.backend.member.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {
    private Long id;
    private String username;
}
