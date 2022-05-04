package ru.kinoday.cinema.cinema.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kinoday.cinema.cinema.model.Movie;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByName(String name);

    @Query("SELECT m FROM Movie m ORDER BY m.added DESC")
    List<Movie> getLastMovies(Pageable pageable);
}
