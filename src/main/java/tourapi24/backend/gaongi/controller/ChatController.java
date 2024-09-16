package tourapi24.backend.gaongi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import tourapi24.backend.gaongi.dto.ChatRequest;
import tourapi24.backend.gaongi.dto.external.ChatBotServerResponse;
import tourapi24.backend.gaongi.service.ChatService;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Tag(name = "Chatbot")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    @Operation(
            summary = "가옹이와 대화합니다",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ChatBotServerResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", content = @Content),
                    @ApiResponse(responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<ChatBotServerResponse> chat(
            @Valid @RequestBody ChatRequest request,
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {
        try {
            return new ResponseEntity<>(chatService.chat(request, userInfo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
