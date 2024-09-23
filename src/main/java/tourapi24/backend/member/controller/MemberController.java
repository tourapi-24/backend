package tourapi24.backend.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tourapi24.backend.annotation.CurrentUser;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.member.dto.profile.BioUpdateRequest;
import tourapi24.backend.member.service.MemberService;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "Member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Member의 프로필 사진을 변경합니다",
            responses = {
                    @ApiResponse(responseCode = "201", description = "프로필 이미지가 성공적으로 업데이트됨"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
            }
    )
    public ResponseEntity<?> updateProfile(
            @RequestPart(value = "profileImage") MultipartFile profileImage,
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {
        try {
            String contentType = profileImage.getContentType();
            if (contentType == null || !contentType.startsWith("image")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            String fileName = memberService.updateProfile(userInfo.getUserId(), profileImage);
            return new ResponseEntity<>(fileName, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("{} {}", e.getClass(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profile/bio")
    @Operation(
            summary = "Member의 bio를 변경합니다",
            responses = {
                    @ApiResponse(responseCode = "201", content = @Content),
                    @ApiResponse(responseCode = "400", content = @Content),
                    @ApiResponse(responseCode = "401", content = @Content)
            }
    )
    public ResponseEntity<?> updateBio(
            @Valid @RequestBody BioUpdateRequest request,
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {
        int result;

        try {
            result = memberService.updateBio(userInfo.getUserId(), request.getContent());
        } catch (Exception e) {
            log.error("{} {}", e.getClass(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (result == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(memberService.updateBio(userInfo.getUserId(), request.getContent()), HttpStatus.CREATED);
    }
}
