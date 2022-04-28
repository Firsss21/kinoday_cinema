package ru.kinoday.cinema.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kinoday.cinema.cinema.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
