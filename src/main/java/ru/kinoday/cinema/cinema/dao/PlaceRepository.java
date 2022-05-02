package ru.kinoday.cinema.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kinoday.cinema.cinema.model.Place;
import ru.kinoday.cinema.cinema.model.PlaceId;

@Repository
public interface PlaceRepository  extends JpaRepository<Place, PlaceId> {

}
