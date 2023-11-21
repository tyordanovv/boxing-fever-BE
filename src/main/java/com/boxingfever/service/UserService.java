package com.boxingfever.service;

import com.boxingfever.api.UserInfoDto;

import java.util.Set;

public interface UserService {
    Set<UserInfoDto> getAllUsersInfo();
    UserInfoDto getUserInfo(Long id);
    void deleteUser(Long id);
}
