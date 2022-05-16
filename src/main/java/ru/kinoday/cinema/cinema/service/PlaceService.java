package ru.kinoday.cinema.cinema.service;


import lombok.AllArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.kinoday.cinema.cinema.dao.MovieRepository;
import ru.kinoday.cinema.cinema.dao.PlaceRepository;
import ru.kinoday.cinema.cinema.model.CinemaHallType;
import ru.kinoday.cinema.cinema.model.Place;
import ru.kinoday.cinema.cinema.model.PlaceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlaceService {

    private PlaceRepository repo;


    private void addPlace(Place place) {
        repo.save(place);
    }

    private void addPlace(int row, int place) {
        repo.save(new Place(row, place));
    }

    @Cacheable(value = "places")
    public Place getPlace(int row, int place) {
        PlaceId placeId = new PlaceId(place, row);
        Optional<Place> byId = repo.findById(placeId);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            addPlace(row, place);
            return getPlace(row, place);
        }
    }

    public List<Place> getPlacesForHallType(CinemaHallType cinemaHallType) {
        List<Place> result = new ArrayList<>();
        int rows = 0;
        int places = 0;
        switch (cinemaHallType) {
            case BIG:{
                rows = 12;
                places = 18;
                break;
            }
            case VIP:{
                rows = 5;
                places = 4;
                break;
            }
            case SMALL:{
                rows = 7;
                places = 10;
                break;
            }
            case MEDIUM:{
                rows = 10;
                places = 15;
                break;
            }
        }

        for (int x = 1; x <= places; x++) {
            for (int y = 1; y <= rows; y++) {
                result.add(getPlace(y, x));
            }
        }

        return result;
    }
}
