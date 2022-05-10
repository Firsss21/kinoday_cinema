package ru.kinoday.cinema.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kinoday.cinema.cinema.model.Movie;
import ru.kinoday.cinema.cinema.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movie")
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;

    @GetMapping(value = "/")
    public List<Movie> getAll() {
        return movieService.getMovies();
    }

    @GetMapping("/last/")
    public List<Movie> getLastMovies(@RequestParam(defaultValue = "5") int count) {
        return movieService.getLastMovies(count);
    }

    @GetMapping(value = "/{id}")
    public Movie getMovie(@PathVariable long id) {
        return movieService.getMovie(id);
    }

}
