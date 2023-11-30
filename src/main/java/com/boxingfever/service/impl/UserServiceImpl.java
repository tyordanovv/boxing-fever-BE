package com.boxingfever.service.impl;

import com.boxingfever.api.user.UpdateUserRequest;
import com.boxingfever.api.user.UserInfoDto;
import com.boxingfever.entity.Role;
import com.boxingfever.entity.User;
import com.boxingfever.exception.APIException;
import com.boxingfever.repository.UserRepository;
import com.boxingfever.repository.RoleRepository;
import com.boxingfever.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

        Set<User> users = userRepository.findAllByRole(role);

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
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "User " + id + " is not found!"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(UpdateUserRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "User " + request.email() + " is not found!"));

        if (!request.email().equals("")){
            user.setEmail(request.email());
        }if (!request.firstName().equals("")){
            user.setFirstName(request.firstName());
        }if (!request.lastName().equals("")){
            user.setLastName(request.lastName());
        }if (!request.address().equals("")){
            user.setAddress(request.address());
        }

        userRepository.save(user);
    }

}
