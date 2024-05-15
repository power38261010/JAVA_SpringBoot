package com.netflix.clon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.netflix.clon.model.Subscription;

/**
 *
 * @author alejandro
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByType(String type);
    boolean existsByType(String type);

    @Query("SELECT COUNT(m) FROM Subscription s JOIN s.movies m WHERE s.type = :type")
    Long countMoviesByType(@Param("type") String type);

    void deleteByType(String type);
    List<Subscription> findByPriceLessThan(double price);
}
