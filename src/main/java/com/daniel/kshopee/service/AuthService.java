package com.daniel.kshopee.service;

import com.daniel.kshopee.payload.LoginDto;
import com.daniel.kshopee.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
