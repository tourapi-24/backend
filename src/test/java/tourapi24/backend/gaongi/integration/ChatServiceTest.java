package tourapi24.backend.gaongi.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.gaongi.domain.Gaongi;
import tourapi24.backend.gaongi.dto.ChatRequest;
import tourapi24.backend.gaongi.dto.external.ChatBotServerResponse;
import tourapi24.backend.gaongi.repository.GaongiRepository;
import tourapi24.backend.gaongi.service.ChatService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @MockBean
    private GaongiRepository gaongiRepository;

    @Mock
    private Gaongi gaongi;

    @Autowired
    @InjectMocks
    private ChatService chatService;

    @Test
    void 채팅() {
        // given
        when(gaongiRepository.findByMemberId(any()))
                .thenReturn(gaongi);
        when(gaongi.getLevel())
                .thenReturn(1);

        String query = "부산 날씨 좋아?";

        CurrentUserInfo userInfo = CurrentUserInfo.builder()
                .userId(1L)
                .username("윤성민")
                .build();
        ChatRequest request = ChatRequest.builder()
                .query(query)
                .build();

        // when
        ChatBotServerResponse response = chatService.chat(request, userInfo);

        // then
        assertNotNull(response);
        System.out.println(response.getAnswer());

    }
}
