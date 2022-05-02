package ru.kinoday.cinema.cinema.service;

import org.springframework.stereotype.Service;
import ru.kinoday.cinema.cinema.model.Cinema;

import java.util.List;

@Service
public class CinemaServiceImplementation implements CinemaService {
    @Override
    public List<Cinema> getAllCinema() {
        return null;
    }

    @Override
    public Cinema getCinemaById(Long id) {
        return null;
    }

    @Override
    public void addCinema(Cinema cinema) {

    }

    @Override
    public void editCinema(Cinema cinema) {

    }
}
