package com.boxingfever.controller;


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

    @GetMapping()
    public ResponseEntity<UserInfoDto> getUser(@RequestBody Long id){
        UserInfoDto response = userService.getUserInfo(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = {"/all"})
    public ResponseEntity<Set<UserInfoDto>> getUsers(){
        Set<UserInfoDto> response = userService.getAllUsersInfo();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(@RequestBody Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
