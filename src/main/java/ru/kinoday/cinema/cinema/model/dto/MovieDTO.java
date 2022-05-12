package ru.kinoday.cinema.cinema.model.dto;

import lombok.Value;
import ru.kinoday.cinema.cinema.model.Genre;

@Value
public class MovieDTO {
     Long id;
     String name;
     String description;
     String mainImagePath;
     Genre genre;
     String country;
     String year;
     Long duration;
     String director;
     String trailer;
     int ageRating;
     float ratingKp;
     float ratingImdb;
}
