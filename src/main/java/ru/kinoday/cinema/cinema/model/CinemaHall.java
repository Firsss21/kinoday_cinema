package ru.kinoday.cinema.cinema.model;

import lombok.AllArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private final String name;

//    @ManyToOne
//    @JoinColumn(name="cinema_id")
//    private final Cinema cinema;

    @Enumerated(EnumType.STRING)
    private final CinemaHallType type;

    @ElementCollection(targetClass = Place.class)
    @JoinTable(name = "cinema_hall_places", joinColumns = @JoinColumn(name = "cinemaHallId"))
    @Column(name = "places")
    private final List<Place> places;

    @ElementCollection(targetClass = Format.class)
    @JoinTable(name = "cinema_hall_formats", joinColumns = @JoinColumn(name = "cinemaHallId"))
    @Column(name = "available_formats")
    @Enumerated(EnumType.STRING)
    private final Set<Format> availableFormats;


    public CinemaHall(String name, CinemaHallType type, List<Place> places, Set<Format> availableFormats) {
        this.name = name;
        this.type = type;
        this.places = places;
        this.availableFormats = availableFormats;
    }
}
