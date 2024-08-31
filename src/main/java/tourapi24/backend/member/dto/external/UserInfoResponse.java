package tourapi24.backend.member.dto.external;

import tourapi24.backend.member.service.auth.UserInfo;

public interface UserInfoResponse {

    UserInfo toUserInfo();
}
