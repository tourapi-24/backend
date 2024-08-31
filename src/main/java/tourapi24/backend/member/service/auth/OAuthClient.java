package tourapi24.backend.member.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.dto.external.UserInfoResponse;


@Component
@RequiredArgsConstructor
public class OAuthClient {

    private final RestTemplate restTemplate;

    public UserInfo fetchUserInfo(Provider provider, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        UserInfoResponse response = restTemplate.exchange(
                provider.getApiUrl(),
                HttpMethod.POST,
                new HttpEntity<>(headers),
                provider.getUserInfoResponse()
        ).getBody();

        if (response == null) {
            throw new IllegalArgumentException("Failed to fetch user info from provider");
        }

        return response.toUserInfo();
    }
}
