package com.moviedetials.cineWorld.Model;
import jakarta.persistence.*;
import lombok.Data;
@Entity (name="movie")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    public Movie(int duration, double rating, String language, Long id, String genre, String title) {
        this.duration = duration;
        this.rating = rating;
        this.language = language;
        this.id = id;
        this.genre = genre;
        this.title = title;
    }

    public Movie() {
    }

    @Column
    private String title;
    private String genre;
    private int duration;
    private String language;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private double rating;




}
