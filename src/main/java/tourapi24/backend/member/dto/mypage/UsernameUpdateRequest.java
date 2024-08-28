package tourapi24.backend.member.dto.mypage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsernameUpdateRequest {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 6)
    private String content;
}
