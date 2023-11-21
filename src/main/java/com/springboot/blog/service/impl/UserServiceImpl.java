package com.springboot.blog.service.impl;

import com.springboot.blog.api.UserInfoDto;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.APIException;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository
    ){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<UserInfoDto> getAllUsersInfo() {
        Role role = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Role is not found!"));

        Set<User> users = userRepository.findAllByRoles(role);

        return users.stream()
                .map(User::toUserInfoDto)
                .collect(Collectors.toSet());
    }

    @Override
    public UserInfoDto getUserInfo(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "User " + id + " is not found!"));

        return user.toUserInfoDto();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
