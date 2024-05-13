package com.netflix.clon.service;

import com.netflix.clon.model.User;

public interface UserService {

    User register(User user);

    User findByUsername(String username);
}
