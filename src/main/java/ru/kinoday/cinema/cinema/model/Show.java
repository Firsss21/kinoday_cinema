package ru.kinoday.cinema.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kinoday.cinema.cinema.model.dto.ScheduleElementDTO;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Show {
    private Map<Integer, Map<Integer, TicketPlace>> places;
    private ScheduleElementDTO scheduleElement;

    public static Show empty() {
        return null;
    }

}
