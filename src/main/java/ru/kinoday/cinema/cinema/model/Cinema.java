package ru.kinoday.cinema.cinema.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "cinema_hall_list")
    @OneToMany
    List<CinemaHall> cinemaHallList;

    @Column
    String description;

    @Column(name = "image_path")
    String imagePath;

    @Column
    String name;


    public Cinema(List<CinemaHall> cinemaHallList, String description, String imagePath, String name) {
        this.cinemaHallList = cinemaHallList;
        this.description = description;
        this.imagePath = imagePath;
        this.name = name;
    }
}
