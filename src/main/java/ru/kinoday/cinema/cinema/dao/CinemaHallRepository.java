package ru.kinoday.cinema.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kinoday.cinema.cinema.model.CinemaHall;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
}
