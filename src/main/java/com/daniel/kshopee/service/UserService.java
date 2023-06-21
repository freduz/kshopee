package com.daniel.kshopee.service;

import com.daniel.kshopee.entity.User;

public interface UserService {

    User getUserByEmail(String email);
    User getCurrentLoggedUser();
}
