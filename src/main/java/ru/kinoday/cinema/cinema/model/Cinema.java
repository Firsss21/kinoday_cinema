package ru.kinoday.cinema.cinema.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "cinema_hall_list")
    @OneToMany
    private List<CinemaHall> cinemaHallList;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @Column
    private String name;


    public Cinema(List<CinemaHall> cinemaHallList, String description, String imagePath, String name) {
        this.cinemaHallList = cinemaHallList;
        this.description = description;
        this.imagePath = imagePath;
        this.name = name;
    }
    public List<CinemaHall> getCinemaHallList() {
        Hibernate.initialize(cinemaHallList);
        return cinemaHallList;
    }
}
