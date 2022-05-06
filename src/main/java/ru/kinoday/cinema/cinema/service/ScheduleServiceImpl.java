package ru.kinoday.cinema.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kinoday.cinema.cinema.dao.ScheduleRepository;
import ru.kinoday.cinema.cinema.model.Movie;
import ru.kinoday.cinema.cinema.model.Schedule;
import ru.kinoday.cinema.cinema.model.ScheduleElement;
import ru.kinoday.cinema.cinema.model.dto.ScheduleElementDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository repo;

    public List<ScheduleElement> getSchedule(Timestamp dateFrom) {
        return repo.findAllByTimeAfterOrderByTime(dateFrom);
    }

    @Override
    public Schedule getSchedule(Timestamp from, Timestamp to, long cinemaId) {
        List<ScheduleElement> list = repo.findAllByTimeAfterAndTimeBeforeAndCinemaId(from, to, cinemaId);

        Map<Long, List<ScheduleElementDTO>> data = new HashMap<>();
        Map<Long, Movie> movies = new HashMap<>();
        for (ScheduleElement scheduleElement : list) {
            Movie movie = scheduleElement.getMovie();
            if (!data.containsKey(movie.getId())) {
                data.put(movie.getId(), new ArrayList<>());
                movies.put(movie.getId(), movie);
            }

            data.get(movie.getId()).add(ScheduleElementDTO.of(scheduleElement));
        }

        return new Schedule(from, to, cinemaId, movies, data);
    }

    @Override
    public List<ScheduleElement> getAllScheduled() {
        return repo.findAll();
    }

    @Override
    public void addSchedule(ScheduleElement element) {
        repo.save(element);
    }

    @Override
    public void editSchedule(ScheduleElement element) {
        repo.save(element);
    }
}
