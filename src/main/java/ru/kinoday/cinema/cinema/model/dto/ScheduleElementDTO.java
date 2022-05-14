package ru.kinoday.cinema.cinema.model.dto;

import lombok.Value;
import ru.kinoday.cinema.cinema.model.CinemaHall;
import ru.kinoday.cinema.cinema.model.Format;
import ru.kinoday.cinema.cinema.model.ScheduleElement;

import java.sql.Timestamp;

@Value
public class ScheduleElementDTO {
    Long id;
    Timestamp startTime;
    Timestamp endTime;
    CinemaHall hall;
    Format format;
    int price;
    boolean started;

    public static ScheduleElementDTO of(ScheduleElement element) {
        return new ScheduleElementDTO(
                element.getId(),
                element.getStartTime(),
                element.getEndTime(),
                element.getHall(),
                element.getFormat(),
                element.getPrice(),
                element.getStartTime().after(new Timestamp(System.currentTimeMillis()))
        );
    }
}
