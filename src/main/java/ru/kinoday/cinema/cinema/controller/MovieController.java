package ru.kinoday.cinema.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kinoday.cinema.cinema.model.dto.MovieDTO;
import ru.kinoday.cinema.cinema.model.dto.NewMovieDto;
import ru.kinoday.cinema.cinema.service.MovieDtoService;

import javax.validation.Valid;
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

    @DeleteMapping(value = "/{id}")
    public void deleteMovie(@PathVariable long id) {
        movieService.deleteMovie(id);
    }

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addMovie(@Valid @RequestBody NewMovieDto movie, BindingResult br) {
        if (!br.hasErrors()) {
            movieService.addMovie(movie.toMovie());
            return ResponseEntity.ok("Фильм добавлен");
        }
        else
            return ResponseEntity.badRequest().body("Ошибка при добавлении фильма");
    }

}
