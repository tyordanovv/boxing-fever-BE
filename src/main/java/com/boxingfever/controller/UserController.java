package com.boxingfever.controller;


import com.boxingfever.api.user.UpdateUserRequest;
import com.boxingfever.api.user.UserInfoDto;
import com.boxingfever.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService
    ){
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserInfoDto> getUser(@PathVariable Long id){
        UserInfoDto response = userService.getUserInfo(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = {"/all"})
    public ResponseEntity<Set<UserInfoDto>> getUsers(){
        Set<UserInfoDto> response = userService.getAllUsersInfo();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request){
        userService.updateUser(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
