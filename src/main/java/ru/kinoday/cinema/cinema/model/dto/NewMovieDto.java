package ru.kinoday.cinema.cinema.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kinoday.cinema.cinema.model.Genre;
import ru.kinoday.cinema.cinema.model.Movie;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewMovieDto
{
    @Size(min = 1, max = 40)
    private String name;
    @Size(min = 1, max = 700)
    private String description;
    @Size(min = 1, max = 100)
    private String mainImagePath;

    private Genre genre;
    @NotEmpty
    @Size(min = 1, max = 100)
    private String country;
    @NotEmpty
    @Size(min = 1, max = 100)
    private String year;
    @NotNull
    private Long duration;
    @NotEmpty
    @Size(min = 1, max = 100)
    private String director;
    @NotEmpty
    @Size(min = 1, max = 100)
    private String trailer;
    @NotNull
    @Max(value = 40)
    private Integer ageRating;
    @NotNull
    private Integer kinopoiskId;

    public Movie toMovie() {
        return new Movie(
                name,
                description,
                mainImagePath,
                genre,
                country,
                year,
                duration * 60 * 1000L,
                director,
                trailer,
                ageRating,
                kinopoiskId
        );
    }
}
