package ru.kinoday.cinema.cinema.service;

import ru.kinoday.cinema.cinema.model.Movie;

import java.util.List;

public interface MovieService {
    void addMovie(Movie movie);
    void editMovie(Movie movie);
    void deleteMovie(long id);
    Movie getMovie(long id);
    Movie getMovie(String name);
    List<Movie> getMovies();
    List<Movie> getLastMovies(int count);
}
