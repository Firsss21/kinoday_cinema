package ru.kinoday.cinema.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.kinoday.cinema.cinema.dao.ScheduleRepository;
import ru.kinoday.cinema.cinema.model.*;
import ru.kinoday.cinema.cinema.model.dto.MovieDTO;
import ru.kinoday.cinema.cinema.model.dto.NewScheduleDto;
import ru.kinoday.cinema.cinema.model.dto.ScheduleElementDTO;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository repo;
    private TicketService ticketService;
    private MovieDtoService movieDtoService;
    private MovieService movieService;
    private CinemaHallService cinemaHallService;
    private CinemaService cinemaService;

    public List<ScheduleElement> getScheduleElement(Timestamp dateFrom) {
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
                movies.put(movie.getId(), movieDtoService.toDto(movie));
            }

            data.get(movie.getId()).add(ScheduleElementDTO.of(scheduleElement, movieDtoService));
        }

        return new Schedule(from, to, cinemaId, movies, data);
    }

    @Override
    public ScheduleElement getScheduleElement(long scheduleId) {
        Optional<ScheduleElement> byId = repo.findById(scheduleId);
        if (byId.isPresent())
            return byId.get();
        else
            return null;
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

        return new Show(places, schedule.getCinema().getId(), ScheduleElementDTO.of(schedule, movieDtoService), movieDtoService.toDto(schedule.getMovie()), ScheduleElementDTO.of(schedule, movieDtoService).isStarted());
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

    @Override
    public ResponseEntity<String> addNewSchedule(NewScheduleDto newSchedule) {
        Timestamp timestampStart = Timestamp.valueOf(newSchedule.getStartTime());
        if (timestampStart.before(new Date())){
            return ResponseEntity.badRequest().body("Укажите время, не раньше настоящего!");
        }
        Movie movie = movieService.getMovie(newSchedule.getMovieId());
        CinemaHall cinemaHallById = cinemaHallService.getCinemaHallById(newSchedule.getCinemaHallId());
        if (!cinemaHallById.getAvailableFormats().contains(newSchedule.getFormat())){
            return ResponseEntity.badRequest().body("Зал \"" + cinemaHallById.getName() + "\" не поддерживает формат " + newSchedule.getFormat().name());
        }
        ScheduleElement scheduleElement = new ScheduleElement(
                timestampStart,
                cinemaService.getCinemaByCinemaHall(cinemaHallById),
                cinemaHallById,
                movie,
                newSchedule.getFormat(),
                Math.toIntExact(newSchedule.getPrice())
        );

        List<ScheduleElement> list = repo.findAllByStartTimeAfterAndStartTimeBeforeAndHallId(
                scheduleElement.getStartTime(), scheduleElement.getEndTime(), scheduleElement.getHall().getId());
        List<ScheduleElement> list2 = repo.findAllByEndTimeAfterAndEndTimeBeforeAndHallId(
                scheduleElement.getStartTime(), scheduleElement.getEndTime(), scheduleElement.getHall().getId());

        if (list.size() > 0){
            return ResponseEntity.badRequest().body("Расписание на это время уже существует " + list.stream().map(ScheduleElement::getId).collect(Collectors.toList()));
        }
        if (list2.size() > 0){
            return ResponseEntity.badRequest().body("Расписание на это время уже существует " + list2.stream().map(ScheduleElement::getId).collect(Collectors.toList()));
        }

        repo.save(scheduleElement);
        return ResponseEntity.ok().body("Расписание добавлено");
    }
}
