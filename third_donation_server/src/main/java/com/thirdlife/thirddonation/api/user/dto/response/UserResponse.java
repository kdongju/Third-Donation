package com.thirdlife.thirddonation.api.user.dto.response;

import com.thirdlife.thirddonation.api.user.dto.UserInfoDto;
import com.thirdlife.thirddonation.common.model.response.BaseResponseBody;
import com.thirdlife.thirddonation.db.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 유저의 일부 정보만 반환 받습니다.
 * description 이 제외되어 있습니다.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@ApiModel("User")
public class UserResponse extends BaseResponseBody {

    @ApiModelProperty(name = "JWT 인증 토큰",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
    private String accessToken;

    private final String tokenType = "Bearer";

    private UserInfoDto data;

    /**
     * 상태 코드와 메시지, 유저 객체를 입력받아 UserResponse 객체를 반환합니다.
     * 액세스 토큰과 같이 보냅니다.
     *
     * @param statusCode Integer
     * @param message    String
     * @param user       User
     * @return UserResponse
     */
    public static UserResponse of(Integer statusCode, String message, String accessToken,
                                  User user) {
        return UserResponse.builder().statusCode(statusCode).message(message)
                .accessToken(accessToken).data(UserInfoDto.of(user)).build();
    }

    /**
     * 상태 코드와 메시지, 유저 객체를 입력받아 UserResponse 객체를 반환합니다.
     *
     * @param statusCode Integer
     * @param message    String
     * @param user       User
     * @return UserResponse
     */
    public static UserResponse of2(Integer statusCode, String message,
                                  User user) {
        return UserResponse.builder().statusCode(statusCode).message(message)
                .data(UserInfoDto.of(user)).build();
    }
}
