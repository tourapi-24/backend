package tourapi24.backend.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourapi24.backend.annotation.CurrentUser;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.member.dto.mypage.UsernameUpdateRequest;
import tourapi24.backend.member.service.MemberService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/username")
    public ResponseEntity<?> updateUsername(
            @Valid @RequestBody UsernameUpdateRequest request,
            @CurrentUser CurrentUserInfo userInfo
    ) {
        int result = memberService.updateUsername(userInfo.getUserId(), request.getContent());

        if (result == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/bio")
    public ResponseEntity<?> updateBio(
            @Valid @RequestBody UsernameUpdateRequest request,
            @CurrentUser CurrentUserInfo userInfo
    ) {
        int result = memberService.updateBio(userInfo.getUserId(), request.getContent());

        if (result == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(memberService.updateBio(userInfo.getUserId(), request.getContent()), HttpStatus.CREATED);
    }
}
