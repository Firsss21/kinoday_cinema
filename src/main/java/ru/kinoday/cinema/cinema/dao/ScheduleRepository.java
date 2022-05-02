package ru.kinoday.cinema.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kinoday.cinema.cinema.model.ScheduleElement;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleElement, Long> {
}
