package ru.kinoday.cinema.cinema.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.hibernate.annotations.CreationTimestamp;
import ru.kinoday.cinema.cinema.model.dto.MovieDTO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column(columnDefinition="TEXT")
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

//    @Column(name = "description_images")
//    private String[] descriptionImages;

    @Column
    private String trailer;

    @Column(name = "age_rating")
    private int ageRating;

    @Column(name = "kp_id")
    private int kinopoiskId;

    public Movie(String name, String description, String mainImagePath, Genre genre, String country, String year, Long duration, String director, String trailer, int ageRating, int kinopoiskId) {
        this.name = name;
        this.description = description;
        this.mainImagePath = mainImagePath;
        this.genre = genre;
        this.country = country;
        this.year = year;
        this.duration = duration;
        this.director = director;
        this.trailer = trailer;
        this.ageRating = ageRating;
        this.kinopoiskId = kinopoiskId;
    }

    public MovieDTO toDto() {
        return null;
    }
}
