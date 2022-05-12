package ru.kinoday.cinema.cinema.service;


import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kinoday.cinema.cinema.dao.MovieRepository;
import ru.kinoday.cinema.cinema.model.Movie;
import ru.kinoday.cinema.cinema.model.dto.MovieDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieDtoService {

    private MovieService service;
    private KinopoiskService kinopoiskService;

    public void addMovie(Movie movie) {
        service.addMovie(movie);
        evictCache();
    }

    public void editMovie(Movie movie) {
        service.editMovie(movie);
        evictCache();
    }

    public void deleteMovie(long id) {
        service.deleteMovie(id);
        evictCache();
    }

    @Cacheable(value = "movie")
    public MovieDTO getMovie(long id) { return toDto(service.getMovie(id)); }

    public MovieDTO toDto(Movie toDto) {
        if (toDto == null)
            return null;

        KinopoiskResponse rating = kinopoiskService.getRating(toDto.getKinopoiskId());

        return new MovieDTO(
                toDto.getId(),
                toDto.getName(),
                toDto.getDescription(),
                toDto.getMainImagePath(),
                toDto.getGenre(),
                toDto.getCountry(),
                toDto.getYear(),
                toDto.getDuration(),
                toDto.getDirector(),
                toDto.getTrailer(),
                toDto.getAgeRating(),
                rating.getRatingKp(),
                rating.getRatingImdb()
        );
    }

    public List<MovieDTO> toDto(List<Movie> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Cacheable(value = "movie")
    public MovieDTO getMovie(String name) { return toDto(service.getMovie(name)); }

    @Cacheable(value = "movie")
    public List<MovieDTO> getMovies() {
        return toDto(service.getMovies());
    }

    @Cacheable(value = "movie")
    public List<MovieDTO> getLastMovies(int count) {
        return toDto(service.getLastMovies(count));
    }

    @Scheduled(fixedDelay = 10 * 60 * 1000)
    @CacheEvict(value = "movie", allEntries = true)
    public void evictCache() {}
}