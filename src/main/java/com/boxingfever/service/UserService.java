package com.boxingfever.service;

import com.boxingfever.api.user.UserInfoDto;
import com.boxingfever.entity.User;

import java.util.Set;

public interface UserService {
    Set<UserInfoDto> getAllUsersInfo();
    UserInfoDto getUserInfo(Long id);
    User getUser(Long id);
    void deleteUser(Long id);
}
