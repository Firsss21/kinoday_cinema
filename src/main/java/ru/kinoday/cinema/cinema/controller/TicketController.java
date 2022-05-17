package ru.kinoday.cinema.cinema.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kinoday.cinema.cinema.model.Order;
import ru.kinoday.cinema.cinema.model.Payment;
import ru.kinoday.cinema.cinema.model.ScheduleElement;
import ru.kinoday.cinema.cinema.model.Ticket;
import ru.kinoday.cinema.cinema.model.dto.TicketDTO;
import ru.kinoday.cinema.cinema.service.ScheduleService;
import ru.kinoday.cinema.cinema.service.TicketService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/tickets/")
public class TicketController {

    private TicketService ticketService;
    private ScheduleService scheduleService;
    private final Gson gson = new Gson();

    @GetMapping("/order")
    public List<TicketDTO> bookTickets(@RequestParam(name = "email", required = true) String email, @RequestParam(name = "order", required = true) String order) {
        Order orderObj = gson.fromJson(order, Order.class);
        ScheduleElement el = scheduleService.getScheduleElement(orderObj.getScheduleId());
        List<TicketDTO> res = ticketService.newTickets(orderObj, email, el).stream().map(Ticket::toDto).collect(Collectors.toList());
        return res;
    }

    @GetMapping
    public List<TicketDTO> getTickets(@RequestParam(name = "email", required = true) String email) {
        return ticketService.getTicketsByEmail(email).stream().map(Ticket::toDto).collect(Collectors.toList());
    }

    @GetMapping("/hash")
    public TicketDTO getTicketsByHash(@RequestParam(name = "hash", required = true) String hash) {
        Ticket ticketByHash = ticketService.getTicketByHash(hash);
        if (ticketByHash != null)
            return ticketByHash.toDto();
        else
            return  null;
    }

    @GetMapping("/use")
    public TicketDTO useTicket(@RequestParam(name = "id", required = true) Long id) {
        return ticketService.useTicket(id).toDto();
    }

    @GetMapping("/pay")
    public String getPaymentLink(@RequestParam(name = "ticketIds", required = false) long[] ticketIds, @RequestParam long uid){
        return ticketService.getPaymentLink(ticketIds, uid);
    }

    @GetMapping("/user/{uid}")
    public ResponseEntity<Boolean> userExist(@PathVariable long uid) {
        return ticketService.userExist(uid);
    }

    @PostMapping(value = "/payment/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Long> processNewPayment(@RequestBody(required = true) String body) {
        Payment payment = gson.fromJson(body, Payment.class);
        if (ticketService.processPayment(payment)) {
            return ResponseEntity.ok(payment.getUid());
        } else {
            return ResponseEntity.badRequest().body(0L);
        }
    }

}
