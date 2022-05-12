package ru.kinoday.cinema.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kinoday.cinema.cinema.model.dto.MovieDTO;
import ru.kinoday.cinema.cinema.service.MovieDtoService;

import java.util.List;

@RestController
@RequestMapping("/movie")
@AllArgsConstructor
public class MovieController {

    private MovieDtoService movieService;

    @GetMapping(value = "/")
    public List<MovieDTO> getAll() {
        return movieService.getMovies();
    }

    @GetMapping("/last/")
    public List<MovieDTO> getLastMovies(@RequestParam(defaultValue = "5") int count) {
        return movieService.getLastMovies(count);
    }

    @GetMapping(value = "/{id}")
    public MovieDTO getMovie(@PathVariable long id) {
        return movieService.getMovie(id);
    }

}
