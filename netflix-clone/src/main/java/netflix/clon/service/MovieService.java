package com.netflix.clon.service;

import com.netflix.clon.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovies();

    Movie getMovieById(Long id);

    Movie saveMovie(Movie movie);

    void deleteMovie(Long id);
}
