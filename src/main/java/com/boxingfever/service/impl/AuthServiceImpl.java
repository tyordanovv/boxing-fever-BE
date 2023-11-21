package com.boxingfever.service.impl;

import com.boxingfever.entity.Role;
import com.boxingfever.entity.User;
import com.boxingfever.exception.APIException;
import com.boxingfever.api.LoginDto;
import com.boxingfever.api.RegisterRequest;
import com.boxingfever.repository.RoleRepository;
import com.boxingfever.repository.UserRepository;
import com.boxingfever.security.JwtTokenProvider;
import com.boxingfever.service.AuthService;
import com.boxingfever.types.UserPlanEnums;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterRequest registerRequest) {
        System.out.println("register AuthServiceImpl");

        // add check for email exists in database
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }
        System.out.println("register AuthServiceImpl2");

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPlanType(UserPlanEnums.STANDARD);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        System.out.println("register AuthServiceImpl3");

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        System.out.println("register AuthServiceImpl4");

        userRepository.save(user);

        return "Registration was successful";
    }
}
