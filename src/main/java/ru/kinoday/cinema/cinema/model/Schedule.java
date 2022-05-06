package ru.kinoday.cinema.cinema.model;

import lombok.Data;
import lombok.Value;
import ru.kinoday.cinema.cinema.model.dto.ScheduleElementDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Value
@Data
public class Schedule {
   Timestamp fromDate;

   Timestamp toDate;

   long cinemaId;

   Map<Long, Movie> movies;

   Map<Long, List<ScheduleElementDTO>> data;
}
