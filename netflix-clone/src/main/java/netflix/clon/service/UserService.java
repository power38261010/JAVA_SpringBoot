package com.netflix.clon.service;

import java.util.List;

import com.netflix.clon.model.Subscription;
import com.netflix.clon.model.User;

/**
 *
 * @author alejandro
 */
public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User findByUsername(String username);

    User findOrCreateUser(String username, String password, boolean isAdmin, Subscription subscription);
}
