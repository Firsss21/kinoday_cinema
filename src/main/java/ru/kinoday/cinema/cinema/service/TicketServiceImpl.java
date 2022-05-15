package ru.kinoday.cinema.cinema.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kinoday.cinema.cinema.dao.TicketRepository;
import ru.kinoday.cinema.cinema.model.*;
import ru.kinoday.cinema.util.RandomGenerator;

import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {
    public TicketServiceImpl(RandomGenerator randomGenerator, TicketRepository ticketRepository) {
        this.randomGenerator = randomGenerator;
        this.ticketRepository = ticketRepository;
    }

    private final RestTemplate restTemplate = new RestTemplate();
    private RandomGenerator randomGenerator;
    private TicketRepository ticketRepository;

    @Value("${service.payment.uri}")
    private String hostAddress;
    private final String path = "/pay/";


    @Override
    public List<Ticket> getTickets(long scheduleId) {
        return ticketRepository.findAllByScheduledId(scheduleId);
    }

    @Override
    public List<Ticket> newTickets(Order order, String email, ScheduleElement el) {
        List<Ticket> tickets = new ArrayList<>();

        if (el == null)
            return new ArrayList<>();

        for (Place place : order.getTickets()) {
            tickets.add(ticketRepository.save(new Ticket(el, place, TicketType.BOOKED, randomGenerator.getRandomAlphabeticString(), email)));
        }

        return tickets;
    }

    @Override
    public String getPaymentLink(long[] ticketIds) {

        int price = 0;
        Set<Long> ticketsToBuy = new HashSet<>();
        String email = "";

        for (long ticketId : ticketIds) {
            Optional<Ticket> byId = ticketRepository.findById(ticketId);
            if (byId.isEmpty())
                continue;

            Ticket ticket = byId.get();

            if (ticket.getType() != TicketType.BOOKED)
                continue;

            price += ticket.getScheduled().getPrice();
            ticketsToBuy.add(ticketId);
            email = ticket.getEmail();
        }

        if (ticketsToBuy.isEmpty())
            return "/profile";

        try {

//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_JSON);
//                JsonObject data = new JsonObject();
//                data.addProperty("price", price);
//                data.addProperty("email", email);
//                JsonArray jArray = new JsonArray();
//                ticketsToBuy.iterator().forEachRemaining(jArray::add);
//                data.add("ticketIds", jArray);
//
//                HttpEntity<String> request =
//                        new HttpEntity<String>(data.toString(), headers);
//                ResponseEntity<String> forEntity = restTemplate.getFor

            Map<String, String> res = Map.of(
                    "price", Integer.toString(price),
                    "email", email,
                    "ticketIds", ticketsToBuy.toString().substring(1, ticketsToBuy.toString().length() - 1));

            String urlTemplate = UriComponentsBuilder.fromHttpUrl(hostAddress + path)
                    .queryParam("email", "{email}")
                    .queryParam("price", "{price}")
                    .queryParam("ticketIds", "{ticketIds}")
                    .encode()
                    .toUriString();

            ResponseEntity<String> forEntity = restTemplate.getForEntity(
                    urlTemplate,
                    String.class,
                    res
            );

            return forEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return "/profile";
    }

    @Override
    public List<Ticket> getTicketsByEmail(String email) {
        List<Ticket> tickets = ticketRepository.findAllByEmail(email);
        return tickets;
    }
}
