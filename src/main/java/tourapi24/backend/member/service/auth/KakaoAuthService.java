package tourapi24.backend.member.service.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.member.domain.Provider;
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

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoUserInfo {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;

        @JsonIgnore
        public UserInfo toUserInfo() {
            return UserInfo.builder()
                    .socialId(String.valueOf(id))
                    .email(kakaoAccount.email)
                    .username(kakaoAccount.profile.nickname)
                    .profileImage(kakaoAccount.profile.isDefaultImage() ? null : kakaoAccount.profile.profileImageUrl)
                    .build();
        }

        @Getter
        @NoArgsConstructor
        @Builder
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class KakaoAccount {
            @JsonProperty("profile")
            private Profile profile;

            @JsonProperty("email")
            private String email;

            @Getter
            @NoArgsConstructor
            @Builder
            @AllArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Profile {
                @JsonProperty("nickname")
                private String nickname;

                @JsonProperty("profile_image_url")
                private String profileImageUrl;

                @JsonProperty("is_default_image")
                private boolean isDefaultImage;

                @JsonProperty("is_default_nickname")
                private boolean isDefaultNickname;
            }
        }
    }
}
