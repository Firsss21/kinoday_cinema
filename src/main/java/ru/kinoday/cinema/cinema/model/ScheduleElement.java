package ru.kinoday.cinema.cinema.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter
@Data
public class ScheduleElement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column
    Timestamp startTime;

    @Column
    Timestamp endTime;

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

    public ScheduleElement(Timestamp start, Cinema cinema, CinemaHall hall, Movie movie, Format format, int price, long adTime) {
        this.startTime = start;
        this.cinema = cinema;
        this.hall = hall;
        this.movie = movie;
        this.format = format;
        this.price = price;
        this.endTime = new Timestamp(this.startTime.getTime() + movie.getDuration() + adTime);
    }

    public ScheduleElement(Timestamp start, Cinema cinema, CinemaHall hall, Movie movie, Format format, int price) {
        this.startTime = start;
        this.cinema = cinema;
        this.hall = hall;
        this.movie = movie;
        this.format = format;
        this.price = price;
        this.endTime = new Timestamp(this.startTime.getTime() + movie.getDuration() + 600_000L);
    }
}
