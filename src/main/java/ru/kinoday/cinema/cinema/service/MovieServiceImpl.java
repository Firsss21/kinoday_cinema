package ru.kinoday.cinema.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kinoday.cinema.cinema.dao.MovieRepository;
import ru.kinoday.cinema.cinema.model.Movie;
import ru.kinoday.cinema.cinema.model.dto.MovieDTO;

import java.util.List;
import java.util.stream.Collectors;

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
    public Movie getMovie(long id) { return repo.findById(id).orElse(null); }

    @Cacheable(value = "movie")
    public Movie getMovie(String name) { return repo.findByName(name).orElse(null); }

    @Override
    public List<Movie> getMovies() {
        return repo.findAll();
    }

    @Override
    public List<Movie> getLastMovies(int count) {
        return repo.getLastMovies(PageRequest.of(0, count));
    }

}
