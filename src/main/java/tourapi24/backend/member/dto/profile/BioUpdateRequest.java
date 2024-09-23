package tourapi24.backend.member.dto.profile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BioUpdateRequest {
    @NotNull
    @Size(max = 20)
    private String content;
}
