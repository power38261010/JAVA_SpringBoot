package com.netflix.clon.seeders;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.netflix.clon.model.Movie;
import com.netflix.clon.model.Subscription;
import com.netflix.clon.model.User;
import com.netflix.clon.repository.MovieRepository;
import com.netflix.clon.repository.SubscriptionRepository;
import com.netflix.clon.repository.UserRepository;

@Component
public class DataSeeder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public User findOrCreateUser(String username, String password, boolean isAdmin, Subscription subscription) {
        User existingUser = userRepository.findByUsername(username);

        if (existingUser != null) {
            return existingUser;
        } else {
            User newUser = new User(username, password, isAdmin);
            newUser.setSubscription(subscription);
            userRepository.save(newUser);

            // Add user to subscription's user set
            // subscription.addUser(newUser);

            return newUser;
        }
    }

    @Transactional
    public Movie findOrCreateMovie(String title, String description, String genre, String url_image, String url_trailer, Set<Subscription> subscriptions) {
        Movie existingMovie = movieRepository.findByTitle(title);

        if (existingMovie != null) {
            return existingMovie;
        } else {
            Movie newMovie = new Movie(title, description, genre);
            newMovie.setUrl_image(url_image);
            newMovie.setUrl_trailer(url_trailer);

            for (Subscription subscription : subscriptions) {
                newMovie.addSubscription(subscription);
                subscription.addMovie(newMovie);
            }

            movieRepository.save(newMovie);
            return newMovie;
        }
    }

    @Transactional
    public Subscription findOrCreateSubscription(String type, double price) {
        Subscription existingSubscription = subscriptionRepository.findByType(type);

        if (existingSubscription != null) {
            return existingSubscription;
        } else {
            Subscription newSubscription = new Subscription(type, price);
            subscriptionRepository.save(newSubscription);
            return newSubscription;
        }
    }
    
    @PostConstruct
    public void seedData() {
        Faker faker = new Faker();
        String[] url_images = {"https://www.google.com/search?q=film+netflix&client=ubuntu-chr&hs=cb&sca_esv=92ceaeaabaa7c149&sca_upv=1&udm=2&biw=1178&bih=530&sxsrf=ADLYWIL75Pt5ORRACx9EhrkqKhV5WIjmiA%3A1715746821624&ei=BThEZozdJfTJ1sQP45aTKA&ved=0ahUKEwjM887-5o6GAxX0pJUCHWPLBAUQ4dUDCA8&uact=5&oq=film+netflix&gs_lp=Egxnd3Mtd2l6LXNlcnAiDGZpbG0gbmV0ZmxpeDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIGEAAYBxgeMgYQABgHGB4yBhAAGAcYHjIGEAAYBxgeSIAMUKsBWIAKcAJ4AJABAJgBaKAB8QKqAQMzLjG4AQPIAQD4AQGYAgagArMDwgIKEAAYgAQYQxiKBcICBhAAGAgYHsICBxAAGIAEGBPCAggQABgTGAcYHpgDAIgGAZIHAzQuMqAHmRU&sclient=gws-wiz-serp#vhid=qnFOxSV0TQqNyM&vssid=mosaic","https://www.google.com/search?q=film+netflix&client=ubuntu-chr&hs=cb&sca_esv=92ceaeaabaa7c149&sca_upv=1&udm=2&biw=1178&bih=530&sxsrf=ADLYWIL75Pt5ORRACx9EhrkqKhV5WIjmiA%3A1715746821624&ei=BThEZozdJfTJ1sQP45aTKA&ved=0ahUKEwjM887-5o6GAxX0pJUCHWPLBAUQ4dUDCA8&uact=5&oq=film+netflix&gs_lp=Egxnd3Mtd2l6LXNlcnAiDGZpbG0gbmV0ZmxpeDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIGEAAYBxgeMgYQABgHGB4yBhAAGAcYHjIGEAAYBxgeSIAMUKsBWIAKcAJ4AJABAJgBaKAB8QKqAQMzLjG4AQPIAQD4AQGYAgagArMDwgIKEAAYgAQYQxiKBcICBhAAGAgYHsICBxAAGIAEGBPCAggQABgTGAcYHpgDAIgGAZIHAzQuMqAHmRU&sclient=gws-wiz-serp#vhid=9pcego3MkLf0bM&vssid=mosaic","https://www.google.com/search?q=film+netflix&client=ubuntu-chr&hs=cb&sca_esv=92ceaeaabaa7c149&sca_upv=1&udm=2&biw=1178&bih=530&sxsrf=ADLYWIL75Pt5ORRACx9EhrkqKhV5WIjmiA%3A1715746821624&ei=BThEZozdJfTJ1sQP45aTKA&ved=0ahUKEwjM887-5o6GAxX0pJUCHWPLBAUQ4dUDCA8&uact=5&oq=film+netflix&gs_lp=Egxnd3Mtd2l6LXNlcnAiDGZpbG0gbmV0ZmxpeDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIGEAAYBxgeMgYQABgHGB4yBhAAGAcYHjIGEAAYBxgeSIAMUKsBWIAKcAJ4AJABAJgBaKAB8QKqAQMzLjG4AQPIAQD4AQGYAgagArMDwgIKEAAYgAQYQxiKBcICBhAAGAgYHsICBxAAGIAEGBPCAggQABgTGAcYHpgDAIgGAZIHAzQuMqAHmRU&sclient=gws-wiz-serp#vhid=GwNfepN1dQWjCM&vssid=mosaic","","https://www.google.com/search?q=film+netflix&client=ubuntu-chr&hs=cb&sca_esv=92ceaeaabaa7c149&sca_upv=1&udm=2&biw=1178&bih=530&sxsrf=ADLYWIL75Pt5ORRACx9EhrkqKhV5WIjmiA%3A1715746821624&ei=BThEZozdJfTJ1sQP45aTKA&ved=0ahUKEwjM887-5o6GAxX0pJUCHWPLBAUQ4dUDCA8&uact=5&oq=film+netflix&gs_lp=Egxnd3Mtd2l6LXNlcnAiDGZpbG0gbmV0ZmxpeDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIGEAAYBxgeMgYQABgHGB4yBhAAGAcYHjIGEAAYBxgeSIAMUKsBWIAKcAJ4AJABAJgBaKAB8QKqAQMzLjG4AQPIAQD4AQGYAgagArMDwgIKEAAYgAQYQxiKBcICBhAAGAgYHsICBxAAGIAEGBPCAggQABgTGAcYHpgDAIgGAZIHAzQuMqAHmRU&sclient=gws-wiz-serp#vhid=ZbDlXPg5CU1wmM&vssid=mosaic","https://www.google.com/search?q=film+netflix&client=ubuntu-chr&hs=cb&sca_esv=92ceaeaabaa7c149&sca_upv=1&udm=2&biw=1178&bih=530&sxsrf=ADLYWIL75Pt5ORRACx9EhrkqKhV5WIjmiA%3A1715746821624&ei=BThEZozdJfTJ1sQP45aTKA&ved=0ahUKEwjM887-5o6GAxX0pJUCHWPLBAUQ4dUDCA8&uact=5&oq=film+netflix&gs_lp=Egxnd3Mtd2l6LXNlcnAiDGZpbG0gbmV0ZmxpeDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIGEAAYBxgeMgYQABgHGB4yBhAAGAcYHjIGEAAYBxgeSIAMUKsBWIAKcAJ4AJABAJgBaKAB8QKqAQMzLjG4AQPIAQD4AQGYAgagArMDwgIKEAAYgAQYQxiKBcICBhAAGAgYHsICBxAAGIAEGBPCAggQABgTGAcYHpgDAIgGAZIHAzQuMqAHmRU&sclient=gws-wiz-serp#vhid=pppzBQOzYNe3-M&vssid=mosaic","https://www.google.com/search?q=film+netflix&client=ubuntu-chr&hs=cb&sca_esv=92ceaeaabaa7c149&sca_upv=1&udm=2&biw=1178&bih=530&sxsrf=ADLYWIL75Pt5ORRACx9EhrkqKhV5WIjmiA%3A1715746821624&ei=BThEZozdJfTJ1sQP45aTKA&ved=0ahUKEwjM887-5o6GAxX0pJUCHWPLBAUQ4dUDCA8&uact=5&oq=film+netflix&gs_lp=Egxnd3Mtd2l6LXNlcnAiDGZpbG0gbmV0ZmxpeDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIGEAAYBxgeMgYQABgHGB4yBhAAGAcYHjIGEAAYBxgeSIAMUKsBWIAKcAJ4AJABAJgBaKAB8QKqAQMzLjG4AQPIAQD4AQGYAgagArMDwgIKEAAYgAQYQxiKBcICBhAAGAgYHsICBxAAGIAEGBPCAggQABgTGAcYHpgDAIgGAZIHAzQuMqAHmRU&sclient=gws-wiz-serp#vhid=hQ6Y_Sot-mrMtM&vssid=mosaic","https://www.google.com/search?q=film+netflix&client=ubuntu-chr&hs=cb&sca_esv=92ceaeaabaa7c149&sca_upv=1&udm=2&biw=1178&bih=530&sxsrf=ADLYWIL75Pt5ORRACx9EhrkqKhV5WIjmiA%3A1715746821624&ei=BThEZozdJfTJ1sQP45aTKA&ved=0ahUKEwjM887-5o6GAxX0pJUCHWPLBAUQ4dUDCA8&uact=5&oq=film+netflix&gs_lp=Egxnd3Mtd2l6LXNlcnAiDGZpbG0gbmV0ZmxpeDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIGEAAYBxgeMgYQABgHGB4yBhAAGAcYHjIGEAAYBxgeSIAMUKsBWIAKcAJ4AJABAJgBaKAB8QKqAQMzLjG4AQPIAQD4AQGYAgagArMDwgIKEAAYgAQYQxiKBcICBhAAGAgYHsICBxAAGIAEGBPCAggQABgTGAcYHpgDAIgGAZIHAzQuMqAHmRU&sclient=gws-wiz-serp#vhid=Q-FdE1g0Ms91CM&vssid=mosaic","https://www.google.com/search?q=film+netflix&client=ubuntu-chr&hs=cb&sca_esv=92ceaeaabaa7c149&sca_upv=1&udm=2&biw=1178&bih=530&sxsrf=ADLYWIL75Pt5ORRACx9EhrkqKhV5WIjmiA%3A1715746821624&ei=BThEZozdJfTJ1sQP45aTKA&ved=0ahUKEwjM887-5o6GAxX0pJUCHWPLBAUQ4dUDCA8&uact=5&oq=film+netflix&gs_lp=Egxnd3Mtd2l6LXNlcnAiDGZpbG0gbmV0ZmxpeDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIGEAAYBxgeMgYQABgHGB4yBhAAGAcYHjIGEAAYBxgeSIAMUKsBWIAKcAJ4AJABAJgBaKAB8QKqAQMzLjG4AQPIAQD4AQGYAgagArMDwgIKEAAYgAQYQxiKBcICBhAAGAgYHsICBxAAGIAEGBPCAggQABgTGAcYHpgDAIgGAZIHAzQuMqAHmRU&sclient=gws-wiz-serp#vhid=3CUKVNwLXH5XBM&vssid=mosaic","https://www.google.com/search?q=film+netflix&client=ubuntu-chr&hs=cb&sca_esv=92ceaeaabaa7c149&sca_upv=1&udm=2&biw=1178&bih=530&sxsrf=ADLYWIL75Pt5ORRACx9EhrkqKhV5WIjmiA%3A1715746821624&ei=BThEZozdJfTJ1sQP45aTKA&ved=0ahUKEwjM887-5o6GAxX0pJUCHWPLBAUQ4dUDCA8&uact=5&oq=film+netflix&gs_lp=Egxnd3Mtd2l6LXNlcnAiDGZpbG0gbmV0ZmxpeDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIGEAAYBxgeMgYQABgHGB4yBhAAGAcYHjIGEAAYBxgeSIAMUKsBWIAKcAJ4AJABAJgBaKAB8QKqAQMzLjG4AQPIAQD4AQGYAgagArMDwgIKEAAYgAQYQxiKBcICBhAAGAgYHsICBxAAGIAEGBPCAggQABgTGAcYHpgDAIgGAZIHAzQuMqAHmRU&sclient=gws-wiz-serp#vhid=kfwUjGibLh3C9M&vssid=mosaic"};
        String url_trailer = "https://www.youtube.com/watch?v=cFS4Zcd_kb8&ab_channel=astounding";

        Subscription started = findOrCreateSubscription("Started", 9.99);
        Subscription premium = findOrCreateSubscription("Premium", 19.99);

        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                findOrCreateUser("admin", "admin1234", true, premium);
            } else if (i % 2 == 0) {
                findOrCreateUser(faker.name().username(), faker.internet().password(), false, started);
            } else {
                findOrCreateUser(faker.name().username(), faker.internet().password(), false, premium);
            }
        }

        for (int i = 0; i < 20; i++) {
            Set<Subscription> subscriptions = new HashSet<>();
            if (i % 2 == 0) {
                subscriptions.add(started);
            } else {
                subscriptions.add(premium);
            }
            findOrCreateMovie(faker.book().title(), faker.lorem().paragraph(), faker.book().genre(), url_images[i], url_trailer, subscriptions);
        }

        System.out.println("Seeders creados exitosamente.");
    }
}

