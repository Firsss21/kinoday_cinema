package ru.kinoday.cinema.cinema.service;

import ru.kinoday.cinema.cinema.model.Cinema;
import ru.kinoday.cinema.cinema.model.CinemaHall;

import java.util.List;

public interface CinemaService {
    List<Cinema> getAllCinema();
    Cinema getCinemaById(Long id);
    void addCinema(Cinema cinema);
    void editCinema(Cinema cinema);

    Cinema getCinemaByCinemaHall(CinemaHall cinemaHall);
}
