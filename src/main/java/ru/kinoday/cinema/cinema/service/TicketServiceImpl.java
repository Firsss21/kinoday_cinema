package ru.kinoday.cinema.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kinoday.cinema.cinema.dao.TicketRepository;
import ru.kinoday.cinema.cinema.model.Ticket;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService
{
    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<Ticket> getTickets(long scheduleId) {
        return ticketRepository.findAllByScheduledId(scheduleId);
    }
}
