package ru.kinoday.cinema.cinema.model;

import lombok.Getter;
import lombok.Value;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "image_path")
    private String mainImagePath;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column
    private String country;

    @Column
    private String year;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added")
    private Date added;

    @Column
    private Long duration;

    @Column
    private String director;

    @Column(name = "description_images")
    private String[] descriptionImages;

    @Column
    private String trailer;

    @Column(name = "age_rating")
    private int ageRating;

    public Movie(String name, String description, String mainImagePath, Genre genre, String country, String year, Long duration, String director, String[] descriptionImages, String trailer, int ageRating) {
        this.name = name;
        this.description = description;
        this.mainImagePath = mainImagePath;
        this.genre = genre;
        this.country = country;
        this.year = year;
        this.duration = duration;
        this.director = director;
        this.descriptionImages = descriptionImages;
        this.trailer = trailer;
        this.ageRating = ageRating;
    }
}
