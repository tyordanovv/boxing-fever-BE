package com.boxingfever.api.util;

import com.boxingfever.api.user.UserInfoDto;
import com.boxingfever.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String role;
    private UserInfoDto user;
}
