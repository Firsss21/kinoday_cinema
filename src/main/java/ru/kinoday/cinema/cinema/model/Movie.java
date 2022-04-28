package ru.kinoday.cinema.cinema.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column
    String name;

    @Column
    String description;

    @Column(name = "image_path")
    String mainImagePath;

//    @Column
//    Genre genre;

    @Column
    String country;

    @Column
    Timestamp date;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added")
    private Date added;

    @Column
    Long duration;

    @Column
    String director;

    @Column(name = "description_images")
    String[] descriptionImages;

    @Column
    String trailer;

    @Column(name = "age_rating")
    int ageRating;
}
