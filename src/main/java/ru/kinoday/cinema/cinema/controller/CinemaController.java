package ru.kinoday.cinema.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kinoday.cinema.cinema.model.Cinema;
import ru.kinoday.cinema.cinema.service.CinemaService;

import java.util.List;

@RestController
@RequestMapping("/cinema")
@AllArgsConstructor
public class CinemaController {

    private CinemaService cinemaService;

    @GetMapping(value = "/")
    public List<Cinema> getAll() {
        return cinemaService.getAllCinema();
    }

    @GetMapping(value = "/{id}")
    public Cinema getById(@PathVariable("id") Long id) {
        return cinemaService.getCinemaById(id);
    }

}
