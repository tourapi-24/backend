package tourapi24.backend.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tourapi24.backend.member.dto.external.KakaoUserInfoResponse;
import tourapi24.backend.member.dto.external.UserInfoResponse;

@Getter
@RequiredArgsConstructor
public enum Provider {
    KAKAO(
            "https://kapi.kakao.com/v2/user/me",
            KakaoUserInfoResponse.class
    ),
    NAVER(null, null);

    private final String apiUrl;
    private final Class<? extends UserInfoResponse> userInfoResponse;
}
