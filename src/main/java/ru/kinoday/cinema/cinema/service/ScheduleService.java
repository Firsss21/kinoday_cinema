package ru.kinoday.cinema.cinema.service;

import ru.kinoday.cinema.cinema.model.Schedule;
import ru.kinoday.cinema.cinema.model.ScheduleElement;
import ru.kinoday.cinema.cinema.model.Show;
import ru.kinoday.cinema.cinema.model.dto.ScheduleElementDTO;

import java.sql.Timestamp;
import java.util.List;

public interface ScheduleService {
    Schedule getSchedule(Timestamp from, Timestamp to, long cinemaId);
    Show getShow(long id);



    void addSchedule(ScheduleElement element);
    List<ScheduleElement> getAllScheduled();
    void editSchedule(ScheduleElement element);
}
