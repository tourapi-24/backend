package tourapi24.backend.member.controller;

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
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/bio")
    public ResponseEntity<?> updateBio(
            @Valid @RequestBody BioUpdateRequest request,
            @CurrentUser CurrentUserInfo userInfo
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
