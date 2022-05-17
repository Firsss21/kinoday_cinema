package ru.kinoday.cinema.cinema.service;

import org.springframework.http.ResponseEntity;
import ru.kinoday.cinema.cinema.model.Order;
import ru.kinoday.cinema.cinema.model.Payment;
import ru.kinoday.cinema.cinema.model.ScheduleElement;
import ru.kinoday.cinema.cinema.model.Ticket;
import ru.kinoday.cinema.cinema.model.dto.TicketDTO;

import java.util.List;

public interface TicketService {

    List<Ticket> getTickets(long scheduleId);

    List<Ticket> newTickets(Order order, String email, ScheduleElement el);

    String getPaymentLink(long[] ticketIds, long uid);

    List<Ticket> getTicketsByEmail(String email);

    ResponseEntity<Boolean> userExist(Long id);

    boolean processPayment(Payment payment);

    Ticket getTicketByHash(String hash);

    Ticket useTicket(Long id);

//    BOOKED,
//    PROCESSING,
//    PURCHASED,
//    USED,
//    EXPIRED,
//    List<Ticket>
}
