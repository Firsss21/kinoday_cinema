package ru.kinoday.cinema.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.kinoday.cinema.cinema.dao.MovieRepository;
import ru.kinoday.cinema.cinema.model.Movie;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private MovieRepository repo;

    @Override
    public void addMovie(Movie movie) {
        repo.save(movie);
    }

    @Override
    public void editMovie(Movie movie) {
        repo.save(movie);
    }

    @Override
    public void deleteMovie(long id) {
        repo.deleteById(id);
    }

    @Override
    @Cacheable(value = "movie")
    public Movie getMovie(long id) {
        return repo.findById(id).orElse(null);
    }
}
