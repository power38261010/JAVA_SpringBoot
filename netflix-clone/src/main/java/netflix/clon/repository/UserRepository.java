package com.netflix.clon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netflix.clon.model.Subscription;
import com.netflix.clon.model.User;

/**
 *
 * @author alejandro
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    List<User> findBySubscription(Subscription subscription);
    void deleteByUsername(String username);
    Long countByIsAdmin(boolean isAdmin);
    boolean existsByUsername(String username);
}
