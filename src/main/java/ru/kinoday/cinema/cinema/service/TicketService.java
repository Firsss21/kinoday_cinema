package ru.kinoday.cinema.cinema.service;

import ru.kinoday.cinema.cinema.model.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> getTickets(long scheduleId);
//    BOOKED,
//    PROCESSING,
//    PURCHASED,
//    USED,
//    EXPIRED,
//    List<Ticket>
}
