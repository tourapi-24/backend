package tourapi24.backend.member.dto.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ProfileImageUpdateRequest {

    private MultipartFile profileImage;
}
