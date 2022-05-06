package ru.kinoday.cinema.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kinoday.cinema.cinema.model.ScheduleElement;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleElement, Long> {
    public List<ScheduleElement> findAllByTimeAfterOrderByTime(Timestamp timestamp);

    public List<ScheduleElement> findAllByTimeAfterAndTimeBeforeAndCinemaId(Timestamp from, Timestamp to, long cinemaId);
}