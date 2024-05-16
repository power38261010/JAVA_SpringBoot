package com.netflix.clon.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netflix.clon.model.Subscription;
import com.netflix.clon.model.User;
import com.netflix.clon.repository.UserRepository;

/**
 *
 * @author alejandro
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setAdmin(user.isAdmin());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     *
     * @param username
     * @param password
     * @param isAdmin
     * @param subscription
     * @return
     */
    @Override
    @Transactional
    public User findOrCreateUser(String username, String password, boolean isAdmin, Subscription subscription) {
        try {
            User existingUser = userRepository.findByUsername(username);

            if (existingUser != null) {
                return existingUser;
            } else {
                User newUser = new User(username, password, isAdmin);
                newUser.setSubscription(subscription);
                userRepository.save(newUser);

                // Add user to subscription's user set
                subscription.addUser(newUser);

                return newUser;
            }
        } catch (Exception e) {
            logger.error("Error al buscar o crear usuario: " + e.getMessage(), e);
            throw new RuntimeException("Error al buscar o crear usuario", e);
        }
    }
}
