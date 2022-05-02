package ru.kinoday.cinema.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kinoday.cinema.cinema.model.Cinema;


@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
