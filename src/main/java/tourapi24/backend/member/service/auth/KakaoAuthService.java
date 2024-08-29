package tourapi24.backend.member.service.auth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.dto.external.KakaoUserInfo;
import tourapi24.backend.member.repository.MemberRepository;

import java.util.Objects;

@Service
public class KakaoAuthService extends AbstractOAuthService {

    public KakaoAuthService(RestTemplate restTemplate, MemberRepository memberRepository, JwtService jwtService) {
        super(restTemplate, memberRepository, jwtService);
    }

    @Override
    protected UserInfo retrieveUserInfoFromProvider(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(url, HttpMethod.POST, entity, KakaoUserInfo.class);

        return Objects.requireNonNull(response.getBody()).toUserInfo();
    }

    @Override
    protected Provider getProvider() {
        return Provider.KAKAO;
    }
}
