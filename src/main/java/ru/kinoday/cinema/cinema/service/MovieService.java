package ru.kinoday.cinema.cinema.service;

import ru.kinoday.cinema.cinema.model.Movie;

public interface MovieService {
    void addMovie(Movie movie);
    void editMovie(Movie movie);
    void deleteMovie(long id);
    Movie getMovie(long id);
}
