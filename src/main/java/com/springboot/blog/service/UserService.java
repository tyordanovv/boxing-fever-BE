package com.springboot.blog.service;

import com.springboot.blog.api.UserInfoDto;
import com.springboot.blog.entity.User;

import java.util.Set;

public interface UserService {
    Set<UserInfoDto> getAllUsersInfo();
    UserInfoDto getUserInfo(Long id);
    void deleteUser(Long id);
}
