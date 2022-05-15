package ru.kinoday.cinema.cinema.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static java.lang.Boolean.TRUE;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

//    @ManyToOne
//    @JoinColumn(name="cinema_id")
//    private final Cinema cinema;

    @Enumerated(EnumType.STRING)
    private CinemaHallType type;

//    @ElementCollection(targetClass = Place.class)
//    @JoinTable(name = "cinema_hall_places", joinColumns = @JoinColumn(name = "cinema_hall_id"))
//    @Column(name = "places")
//    @ManyToOne
    @Column(name = "cinema_hall_places")
    @ManyToMany
    private List<Place> places;

    @ElementCollection(targetClass = Format.class)
    @JoinTable(name = "cinema_hall_formats", joinColumns = @JoinColumn(name = "cinema_hall_id"))
    @Column(name = "available_formats")
    @Enumerated(EnumType.STRING)
    private Set<Format> availableFormats;


    public CinemaHall(String name, CinemaHallType type, List<Place> places, Set<Format> availableFormats) {
        this.name = name;
        this.type = type;
        this.places = places;
        this.availableFormats = availableFormats;
    }
}
