package tourapi24.backend.member.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.service.auth.UserInfo;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponse implements UserInfoResponse {
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
                .provider(Provider.KAKAO)
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
