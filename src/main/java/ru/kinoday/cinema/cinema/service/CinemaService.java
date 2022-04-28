package ru.kinoday.cinema.cinema.service;

import ru.kinoday.cinema.cinema.model.Cinema;

import java.util.List;

public interface CinemaService {
    List<Cinema> getAllCinema();
    Cinema getCinemaById(Long id);
}
