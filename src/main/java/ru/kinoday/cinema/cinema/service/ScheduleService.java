package ru.kinoday.cinema.cinema.service;

import ru.kinoday.cinema.cinema.model.ScheduleElement;

import java.sql.Timestamp;
import java.util.List;

public interface ScheduleService {
    List<ScheduleElement> getMovies(Timestamp dateFrom);
}
