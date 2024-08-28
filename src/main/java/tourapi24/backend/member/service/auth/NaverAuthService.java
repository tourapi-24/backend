package tourapi24.backend.member.service.auth;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.repository.MemberRepository;

@Service
public class NaverAuthService extends AbstractOAuthService {

    public NaverAuthService(RestTemplate restTemplate, MemberRepository memberRepository) {
        super(restTemplate, memberRepository);
    }

    @Override
    protected UserInfo retrieveUserInfoFromProvider(String accessToken) {
        // Implement Naver-specific user info retrieval
        throw new UnsupportedOperationException("Naver OAuth is not implemented yet");
    }

    @Override
    protected Provider getProvider() {
        return Provider.NAVER;
    }
}
