package ru.kinoday.cinema.cinema.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kinoday.cinema.cinema.dao.TicketRepository;
import ru.kinoday.cinema.cinema.model.*;
import ru.kinoday.cinema.util.EmailService;
import ru.kinoday.cinema.util.RandomGenerator;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static ru.kinoday.cinema.cinema.model.TicketType.*;

@Service
public class TicketServiceImpl implements TicketService {

    public TicketServiceImpl(RandomGenerator randomGenerator, TicketRepository ticketRepository, EmailService emailService) {
        this.randomGenerator = randomGenerator;
        this.ticketRepository = ticketRepository;
        this.emailService = emailService;
    }

    private final RestTemplate restTemplate = new RestTemplate();
    private RandomGenerator randomGenerator;
    private TicketRepository ticketRepository;
    private EmailService emailService;

    @Value("${service.payment.uri}")
    private String hostAddress;
    private final String path = "/pay/";

    @Value("${service.front.uri}")
    private String hostAddressFront;
    private final String pathFront = "/user/";


    @Override
    public List<Ticket> getTickets(long scheduleId) {
        return ticketRepository.findAllByScheduledId(scheduleId);
    }

    @Override
    public List<Ticket> newTickets(Order order, String email, ScheduleElement el) {
        List<Ticket> tickets = new ArrayList<>();

        if (el == null)
            return new ArrayList<>();

        List<Place> places = order.getTickets().stream().map(PlaceDto::toPlace).collect(Collectors.toList());

        for (Place place : places) {
            tickets.add(ticketRepository.save(new Ticket(el, place, TicketType.BOOKED, randomGenerator.getRandomAlphabeticString(6), email)));
        }

        return tickets;
    }

    @Override
    public String getPaymentLink(long[] ticketIds, long uid) {

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

            Map<String, String> res = Map.of(
                    "price", Integer.toString(price),
                    "email", email,
                    "uid", String.valueOf(uid),
                    "ticketIds", ticketsToBuy.toString().substring(1, ticketsToBuy.toString().length() - 1));

            String urlTemplate = UriComponentsBuilder.fromHttpUrl(hostAddress + path)
                    .queryParam("email", "{email}")
                    .queryParam("price", "{price}")
                    .queryParam("ticketIds", "{ticketIds}")
                    .queryParam("uid", "{uid}")
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
            return "/profile";
        }
    }

    @Override
    public List<Ticket> getTicketsByEmail(String email) {
        List<Ticket> tickets = ticketRepository.findAllByEmail(email);
        return tickets;
    }

    @Override
    public ResponseEntity<Boolean> userExist(Long id) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(hostAddressFront + pathFront + id)
                .encode()
                .toUriString();

        try {
            return restTemplate.getForEntity(
                    urlTemplate,
                    Boolean.class
            );
        } catch (RestClientException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @Override
    public boolean processPayment(Payment payment) {
        // set tickets new status
        List<Ticket> tickets = new ArrayList<>();
        for (Long ticketId : payment.getTicketIds()) {
            Optional<Ticket> byId = ticketRepository.findById(ticketId);
            if (byId.isPresent() && byId.get().getType() != PURCHASED) {
                byId.get().setType(PURCHASED);
                ticketRepository.save(byId.get());
                tickets.add(byId.get());
            }
        }

        if (tickets.size() == 0)
            return true;

        String msg = "Вы приобрели билеты: ";

        for (Ticket ticket : tickets) {
            String time = new SimpleDateFormat("dd MMM HH:mm").format(ticket.getScheduled().getStartTime());
            msg += "\n\nКинотеатр: " + ticket.getScheduled().getCinema().getName() + ", " +
                    "фильм: " + ticket.getScheduled().getMovie().getName() + ", " +
                    "время " + time + ", " +
                    "время " + ticket.getScheduled().getStartTime() + ", " +
                    "\nПерсональный код, для подтверждения покупки билета [" + ticket.getPersonalHashCode() + "]. ";
        }

        emailService.sendSimpleMessage(
                payment.getEmail(),
                "Кинодень - Ваши билеты в кинотеатр!",
                msg
        );
        return true;
    }

    @Override
    public Ticket getTicketByHash(String hash) {
        return ticketRepository.findAllByPersonalHashCode(hash);
    }

    @Override
    public Ticket useTicket(Long id) {
        Optional<Ticket> byId = ticketRepository.findById(id);
        Ticket ticket = byId.get();
        ticket.setType(USED);
        ticketRepository.save(ticket);
        return ticket;
    }

    @Scheduled(fixedDelay = 30 * 1000L)
    private void updateTicketStatus() {
        List<Ticket> all = ticketRepository.findAll();
        for (Ticket ticket : all) {
            switch (ticket.getType()) {
                case PURCHASED: {
                    long diff = ticket.getScheduled().getStartTime().getTime() - System.currentTimeMillis();//as given
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                    if (minutes < 15) {
                        continue;
                    }

                    ticket.setType(EXPIRED);
                    ticketRepository.save(ticket);
                    break;
                }

                case BOOKED: {
                    long diff = System.currentTimeMillis() - ticket.getScheduled().getStartTime().getTime();
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

                    long diffCreated = System.currentTimeMillis() - ticket.getAdded().getTime();
                    long minutesCreatedAgo = TimeUnit.MILLISECONDS.toMinutes(diffCreated);

                    if (minutes > 15 || minutesCreatedAgo < 5) {
                        continue;
                    }

                    ticketRepository.delete(ticket);
                    break;
                }
            }
        }
    }
}
