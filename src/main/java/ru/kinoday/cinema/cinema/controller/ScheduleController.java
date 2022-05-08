package ru.kinoday.cinema.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kinoday.cinema.cinema.model.Schedule;
import ru.kinoday.cinema.cinema.model.Show;
import ru.kinoday.cinema.cinema.model.dto.ScheduleElementDTO;
import ru.kinoday.cinema.cinema.service.ScheduleService;

import java.sql.Timestamp;

@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
public class ScheduleController {

    ScheduleService scheduleService;

    @GetMapping("/")
    public Schedule getSchedule(
            @RequestParam Timestamp from,
            @RequestParam Timestamp to,
            @RequestParam long id
            ) {
        Schedule schedule = scheduleService.getSchedule(from, to, id);

        return schedule;
    }

    @GetMapping(value = "/{id}")
    public Show getShow(
            @PathVariable long id
    ) {
        return scheduleService.getShow(id);
    }
}
