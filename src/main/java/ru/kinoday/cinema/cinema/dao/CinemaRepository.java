package ru.kinoday.cinema.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kinoday.cinema.cinema.model.Cinema;
import ru.kinoday.cinema.cinema.model.CinemaHall;


@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Cinema findByCinemaHallListContains(CinemaHall cinemaHall);
}
