package ru.kinoday.cinema.cinema.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;
import ru.kinoday.cinema.cinema.model.dto.MovieDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@ToString
@Component
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

    public MovieDTO toDtoWithoutRating() {
        return new MovieDTO(
                this.getId(),
                this.getName(),
                this.getDescription(),
                this.getMainImagePath(),
                this.getGenre(),
                this.getCountry(),
                this.getYear(),
                this.getDuration(),
                this.getDirector(),
                this.getTrailer(),
                this.getAgeRating(),
                0f,
                0f
        );
    }
}
