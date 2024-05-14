package com.netflix.clon.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private double price;

    @ManyToMany(mappedBy = "subscriptions")
    private Set<Movie> movies;

    @OneToMany(mappedBy = "subscription")
    private Set<User> users;

    public Subscription() {
    }

    public Subscription(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
