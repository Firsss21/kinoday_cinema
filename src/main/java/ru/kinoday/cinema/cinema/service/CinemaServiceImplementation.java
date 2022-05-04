package ru.kinoday.cinema.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kinoday.cinema.cinema.dao.CinemaRepository;
import ru.kinoday.cinema.cinema.model.Cinema;

import java.util.List;

@Service
@AllArgsConstructor
public class CinemaServiceImplementation implements CinemaService {

    CinemaRepository repo;

    @Override
    public List<Cinema> getAllCinema() {
        return repo.findAll();
    }

    @Override
    public Cinema getCinemaById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void addCinema(Cinema cinema) {
        repo.save(cinema);
    }

    @Override
    public void editCinema(Cinema cinema) {
        repo.save(cinema);
    }
}
