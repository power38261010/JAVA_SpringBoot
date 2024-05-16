package com.netflix.clon.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netflix.clon.model.Movie;
import com.netflix.clon.model.Subscription;
import com.netflix.clon.repository.MovieRepository;
import com.netflix.clon.repository.SubscriptionRepository;
   

/**
 *
 * @author alejandro
 */
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final SubscriptionRepository subscriptionRepository;
    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, SubscriptionRepository subscriptionRepository) {
        this.movieRepository = movieRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        return movieOptional.orElse(null);
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Long id, Movie movie) {
        Movie existingMovie = getMovieById(id);
        if (existingMovie != null) {
            existingMovie.setTitle(movie.getTitle());
            existingMovie.setDescription(movie.getDescription());
            existingMovie.setGenre(movie.getGenre());
            return movieRepository.save(existingMovie);
        }
        return null; // Movie not found
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    /**
     *
     * @param title
     * @param description
     * @param genre
     * @param url_image
     * @param url_trailer
     * @param subscriptionTypes
     * @return
     */
    @Override
    @Transactional
    public Movie findOrCreateMovie(String title, String description, String genre, String url_image, String url_trailer, Set<Subscription> subscriptionTypes) {
        try {
            Movie existingMovie = movieRepository.findByTitle(title);

            if (existingMovie != null) {
                return existingMovie;
            } else {
                Movie newMovie = new Movie(title, description, genre);
                newMovie.setUrl_image(url_image);
                newMovie.setUrl_trailer(url_trailer);

                Set<Subscription> subscriptions = new HashSet<>();
                for (Subscription subscriptionType : subscriptionTypes) {
                    Subscription subscription = subscriptionRepository.findByType(subscriptionType.getType());
                    if (subscription != null) {
                        subscriptions.add(subscription);
                        // subscription.addMovie(newMovie); // Ensure bidirectional relationship
                    }
                }

                newMovie.setSubscriptions(subscriptions); // Set the managed subscriptions

                movieRepository.save(newMovie);
                return newMovie;
            }
        } catch (Exception e) {
            logger.error("Error al buscar o crear película: " + e.getMessage(), e);
            throw new RuntimeException("Error al buscar o crear película", e);
        }
    }
}
