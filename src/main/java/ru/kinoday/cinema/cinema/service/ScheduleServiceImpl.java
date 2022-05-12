package ru.kinoday.cinema.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kinoday.cinema.cinema.dao.ScheduleRepository;
import ru.kinoday.cinema.cinema.model.*;
import ru.kinoday.cinema.cinema.model.dto.MovieDTO;
import ru.kinoday.cinema.cinema.model.dto.ScheduleElementDTO;

import java.sql.Timestamp;
import java.util.*;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository repo;
    private TicketService ticketService;
    private MovieDtoService movieService;

    public List<ScheduleElement> getSchedule(Timestamp dateFrom) {
        return repo.findAllByStartTimeAfterOrderByStartTime(dateFrom);
    }

    @Override
    public Schedule getSchedule(Timestamp from, Timestamp to, long cinemaId) {
        List<ScheduleElement> list = repo.findAllByStartTimeAfterAndStartTimeBeforeAndCinemaIdOrderByStartTime(from, to, cinemaId);

        Map<Long, List<ScheduleElementDTO>> data = new HashMap<>();
        Map<Long, MovieDTO> movies = new HashMap<>();
        for (ScheduleElement scheduleElement : list) {
            Movie movie = scheduleElement.getMovie();
            if (!data.containsKey(movie.getId())) {
                data.put(movie.getId(), new ArrayList<>());
                movies.put(movie.getId(), movieService.toDto(movie));
            }

            data.get(movie.getId()).add(ScheduleElementDTO.of(scheduleElement));
        }

        return new Schedule(from, to, cinemaId, movies, data);
    }

    @Override
    public List<ScheduleElement> getAllScheduled() {
        return repo.findAll();
    }

    @Override
    public void addSchedule(ScheduleElement element) {
        repo.save(element);
    }

    @Override
    public void editSchedule(ScheduleElement element) {
        repo.save(element);
    }

    @Override
    public Show getShow(long scheduleId) {

        Optional<ScheduleElement> optionalSchedule = repo.findById(scheduleId);

        if (optionalSchedule.isEmpty())
            return Show.empty();

        ScheduleElement schedule = optionalSchedule.get();
        List<Ticket> tickets = ticketService.getTickets(scheduleId);
        Map<Integer, Map<Integer, TicketPlace>> places = fillEmpty(schedule);
        for (Ticket ticket : tickets) {
            Place p = ticket.getPlace();
            TicketPlace ticketPlace = places.get(p.getRow()).get(p.getPlace());
            ticketPlace.setCanOrder(ticket.getType() == TicketType.AVAILABLE);
        }

        return new Show(places, ScheduleElementDTO.of(schedule));
    }

    private Map<Integer, Map<Integer, TicketPlace>> fillEmpty(ScheduleElement schedule) {

        Map<Integer, Map<Integer, TicketPlace>> emptyPlaces = new HashMap<>();

        List<Place> places = schedule.getHall().getPlaces();
        for (Place p : places) {
            int row = p.getRow();
            int place = p.getPlace();

            if (!emptyPlaces.containsKey(row)) {
                emptyPlaces.put(row, new HashMap<>());
            }

            Map<Integer, TicketPlace> rowMap = emptyPlaces.get(row);
            rowMap.put(place, new TicketPlace(schedule.getPrice(), row, place, true));
        }

        return emptyPlaces;
    }
}
