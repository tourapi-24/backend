package tourapi24.backend.gaongi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.gaongi.domain.Gaongi;
import tourapi24.backend.gaongi.dto.ChatRequest;
import tourapi24.backend.gaongi.dto.external.ChatBotServerResponse;
import tourapi24.backend.gaongi.dto.external.ChatbotServerRequest;
import tourapi24.backend.gaongi.repository.GaongiRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final RestTemplate restTemplate;
    private final GaongiRepository gaongiRepository;

    @Value("${key.chatbot}")
    private String key;

    public ChatBotServerResponse chat(ChatRequest request, CurrentUserInfo userInfo) {
        Gaongi gaongi = gaongiRepository.findByMemberId(userInfo.getUserId());

        ChatbotServerRequest chatbotServerRequest = ChatbotServerRequest.builder()
                .query(request.getQuery())
                .name(userInfo.getUsername())
                .level(gaongi.getLevel())
                .key(key)
                .build();

        return restTemplate.exchange(
                "https://chatbot-api.seongmin.dev/chatbot",
                HttpMethod.POST,
                new HttpEntity<>(chatbotServerRequest),
                ChatBotServerResponse.class
        ).getBody();
    }
}
