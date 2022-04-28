package ru.kinoday.cinema.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kinoday.cinema.cinema.model.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
