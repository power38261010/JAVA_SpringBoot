package com.netflix.clon.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String genre;
    private Text url_image;
    private Text url_trailer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_subscription",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id")
    )
    private Set<Subscription> subscriptions;

    public Movie() {
    }

    public Movie(String title, String description, String genre) {
        this.title = title;
        this.description = description;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Text getUrl_image() {
        return url_image;
    }

    public void setDescription(Text url_image) {
        this.url_image = url_image;
    }


    public Text getUrl_trailer() {
        return url_trailer;
    }

    public void setUrl_trailer(Text url_trailer) {
        this.url_trailer = url_trailer;
    }
}
