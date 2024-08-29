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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourapi24.backend.annotation.CurrentUser;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.member.dto.mypage.BioUpdateRequest;
import tourapi24.backend.member.service.MemberService;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "Member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/bio")
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
