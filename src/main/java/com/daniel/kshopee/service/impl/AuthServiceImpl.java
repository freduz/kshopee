package com.daniel.kshopee.service.impl;

import com.daniel.kshopee.entity.Role;
import com.daniel.kshopee.entity.User;
import com.daniel.kshopee.payload.LoginDto;
import com.daniel.kshopee.payload.RegisterDto;
import com.daniel.kshopee.repository.RoleRepository;
import com.daniel.kshopee.repository.UserRepository;
import com.daniel.kshopee.security.JwtTokenProvider;
import com.daniel.kshopee.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider
    ){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public String login(LoginDto loginDto) {
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())){
         log.info("already exists");
        }



        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(registerDto.getRole()).get();
        roles.add(userRole);
        System.out.println(roles);

        User user = User.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
}
