package ru.kinoday.cinema.cinema.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter
public class ScheduleElement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column
    Timestamp time;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    CinemaHall hall;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    @Enumerated(EnumType.STRING)
    Format format;

    @Column
    int price;

    public ScheduleElement(Timestamp time, Cinema cinema, CinemaHall hall, Movie movie, Format format, int price) {
        this.time = time;
        this.cinema = cinema;
        this.hall = hall;
        this.movie = movie;
        this.format = format;
        this.price = price;
    }
}
