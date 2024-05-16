package com.netflix.clon.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column( nullable = true)
    private String description;

    @Column( nullable = true)
    private String genre;

    @Column(columnDefinition = "TEXT", unique = false, nullable = true)
    private String url_image;

    @Column(columnDefinition = "TEXT",unique = false, nullable = true)
    private String url_trailer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_subscription",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id")
    )
    private Set<Subscription> subscriptions = new HashSet<>();

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

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }


    public String getUrl_trailer() {
        return url_trailer;
    }

    public void setUrl_trailer(String url_trailer) {
        this.url_trailer = url_trailer;
    }

    /**
     *
     * @param subscription
     */
    public void addSubscription(Subscription subscription) {
        if (subscriptions == null) {
            subscriptions = new HashSet<>();
        }
        subscriptions.add(subscription);
    }
}
