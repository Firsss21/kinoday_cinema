package ru.kinoday.cinema.cinema.model;

import javax.persistence.*;

@Entity
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column
    String name;

    @ManyToOne
    @JoinColumn(name="cinema_id")
    Cinema cinema;

//    @Column
//    CinemaHallType type;

//    @Column(name = "available_formats")
//    Format[] availableFormats;
}
