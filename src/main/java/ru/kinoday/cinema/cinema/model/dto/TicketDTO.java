package ru.kinoday.cinema.cinema.model.dto;

import lombok.Value;
import ru.kinoday.cinema.cinema.model.Place;
import ru.kinoday.cinema.cinema.model.TicketType;

@Value
public class TicketDTO {
    Long id;
    ScheduleElementDTO scheduled;
    Place place;
    TicketType type;
    String personalHashCode;
    String email;
}
