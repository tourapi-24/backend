package tourapi24.backend.travellog.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourapi24.backend.annotation.CurrentUser;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.travellog.domain.TravelLog;
import tourapi24.backend.travellog.dto.TravelLogCreateRequest;
import tourapi24.backend.travellog.service.TravelLogService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travellog")
public class TravelLogController {

    private final TravelLogService travelLogService;

    @PostMapping
    public ResponseEntity<?> createTravelLog(
            @RequestBody TravelLogCreateRequest request,
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {
        TravelLog travelLog = travelLogService.createTravelLog(userInfo, request);
        return ResponseEntity.created(URI.create("/travellog/" + travelLog.getId())).build();
    }
}
