package com.daniel.kshopee.service.impl;


import com.daniel.kshopee.entity.User;
import com.daniel.kshopee.repository.UserRepository;
import com.daniel.kshopee.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getCurrentLoggedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No user found"));

        return user;
    }
}
