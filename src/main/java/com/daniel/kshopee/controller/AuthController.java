package com.daniel.kshopee.controller;


import com.daniel.kshopee.payload.JwtAuthResponse;
import com.daniel.kshopee.payload.LoginDto;
import com.daniel.kshopee.payload.RegisterDto;
import com.daniel.kshopee.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping(value = {"/sign-in","/login"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        log.info(loginDto.toString());
        String token = authService.login(loginDto);
        log.info(loginDto.toString());
        JwtAuthResponse authResponse = JwtAuthResponse.builder()
                .accessToken(token)
                .build();
        return new ResponseEntity<JwtAuthResponse>(authResponse,HttpStatus.CREATED);
    }

    @PostMapping(value = {"/sign-up","/register"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        log.info(registerDto.toString());
       String response = this.authService.register(registerDto);
       return new ResponseEntity<String>(response,HttpStatus.CREATED);
    }

}
