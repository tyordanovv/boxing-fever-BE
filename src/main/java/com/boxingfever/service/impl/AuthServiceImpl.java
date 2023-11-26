package com.boxingfever.service.impl;

import com.boxingfever.api.util.JWTAuthResponse;
import com.boxingfever.entity.Role;
import com.boxingfever.entity.User;
import com.boxingfever.exception.APIException;
import com.boxingfever.api.user.LoginDto;
import com.boxingfever.api.user.RegisterRequest;
import com.boxingfever.repository.RoleRepository;
import com.boxingfever.repository.UserRepository;
import com.boxingfever.security.JwtTokenProvider;
import com.boxingfever.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

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
    public JWTAuthResponse login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.email(), loginDto.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        String token = jwtTokenProvider.generateToken(authentication);
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);

        User user = userRepository.findByEmail(loginDto.email()).get();

        JWTAuthResponse response = new JWTAuthResponse();

        response.setAccessToken(token);
        response.setRole(role);
        response.setUser(user.toUserInfoDto());

        return response;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        // add check for email exists in database
        if(userRepository.existsByEmail(registerRequest.email())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        User user = new User();
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setAddress(registerRequest.address());
        user.setEmail(registerRequest.email());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));

        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRole(userRole);

        userRepository.save(user);
    }
}
