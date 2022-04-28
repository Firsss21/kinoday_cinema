package ru.kinoday.cinema.cinema.model;

import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class ScheduleElement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

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

    @Column
    Restrictions[] restrictions;

//    @Column
//    Format format;

    @Column
    int price;
}
