package tourapi24.backend.gaongi.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourapi24.backend.annotation.CurrentUser;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.gaongi.dto.GaongiResponse;
import tourapi24.backend.gaongi.service.GaongiService;

@RestController
@RequestMapping("/gaongi")
@RequiredArgsConstructor
public class GaongiController {

    private final GaongiService gaongiService;

    @GetMapping
    public ResponseEntity<GaongiResponse> getGaongi(
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {
        return new ResponseEntity<>(
                gaongiService.getGaongi(userInfo.getUserId()),
                HttpStatus.OK
        );
    }

    @PostMapping("/exp/increase")
    public ResponseEntity<GaongiResponse> increaseExp(
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {
        return new ResponseEntity<>(
                gaongiService.increaseExp(userInfo.getUserId()),
                HttpStatus.CREATED
        );
    }
}
