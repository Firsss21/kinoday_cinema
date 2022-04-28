package ru.kinoday.cinema.cinema.service;

import org.springframework.stereotype.Component;
import ru.kinoday.cinema.cinema.model.Ticket;

import java.util.ArrayList;
import java.util.List;

@Component
public class TickedCheckerService {

    public List<Ticket> checkExpiredTickets() {
        List<Ticket> list = new ArrayList<>();

        return list;
    }
}
