package com.netflix.clon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netflix.clon.model.Movie;
import com.netflix.clon.model.Subscription;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
    List<Movie> findBySubscriptions(Subscription subscription);
    void deleteByTitle(String title);
    Long countByGenre(String genre);
    Movie findByTitleIgnoreCase(String title);
    List<Movie> findByGenre(String genre);
}