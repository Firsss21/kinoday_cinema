package ru.kinoday.cinema.cinema.service;

import ru.kinoday.cinema.cinema.model.Order;
import ru.kinoday.cinema.cinema.model.ScheduleElement;
import ru.kinoday.cinema.cinema.model.Ticket;
import ru.kinoday.cinema.cinema.model.dto.TicketDTO;

import java.util.List;

public interface TicketService {

    List<Ticket> getTickets(long scheduleId);

    List<Ticket> newTickets(Order order, String email, ScheduleElement el);

    String getPaymentLink(long[] ticketIds);

    List<Ticket> getTicketsByEmail(String email);

//    BOOKED,
//    PROCESSING,
//    PURCHASED,
//    USED,
//    EXPIRED,
//    List<Ticket>
}
