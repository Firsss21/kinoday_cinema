package ru.kinoday.cinema.cinema.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kinoday.cinema.cinema.model.Schedule;
import ru.kinoday.cinema.cinema.model.Show;
import ru.kinoday.cinema.cinema.model.dto.NewScheduleDto;
import ru.kinoday.cinema.cinema.service.ScheduleService;

import javax.validation.Valid;
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

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addSchedule(@Valid @RequestBody NewScheduleDto newSchedule, BindingResult br) {
        if (!br.hasErrors())
            return scheduleService.addNewSchedule(newSchedule);

        return ResponseEntity.badRequest().body("Неверное расписание");
    }

    @DeleteMapping("/{sid}")
    public void removeSchedule(@PathVariable("sid") long id) {
        scheduleService.removeSchedule(id);
    }
}
