package com.netflix.clon.service;

import java.util.List;
import java.util.Set;

import com.netflix.clon.model.Movie;
import com.netflix.clon.model.Subscription;

/**
 *
 * @author alejandro
 */
public interface MovieService {

    List<Movie> getAllMovies();

    Movie getMovieById(Long id);

    Movie saveMovie(Movie movie);

    Movie updateMovie(Long id, Movie movie);

    void deleteMovie(Long id);

    Movie findOrCreateMovie(String title, String description, String genre, String url_image, String url_trailer, Set<Subscription> subscriptionTypes) ;
}
