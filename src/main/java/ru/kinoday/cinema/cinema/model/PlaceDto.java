package ru.kinoday.cinema.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PlaceDto {
    private int row;
    private int place;

    public Place toPlace() {
        return new Place(
                row, place
        );
    }
}
