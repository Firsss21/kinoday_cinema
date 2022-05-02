package ru.kinoday.cinema.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kinoday.cinema.cinema.dao.CinemaHallRepository;
import ru.kinoday.cinema.cinema.model.CinemaHall;

import java.util.List;

@Service
@AllArgsConstructor
public class CinemaHallService {

    private CinemaHallRepository repo;

    public CinemaHall addCinemaHall(CinemaHall hall) {
        this.repo.save(hall);
        return hall;
    }

    public CinemaHall getCinemaHallById(long id) {
        return this.repo.findById(id).orElse(null);
    }

    public List<CinemaHall> getAllCinemaHalls() {
        return this.repo.findAll();
    }
}
