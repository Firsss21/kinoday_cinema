package ru.kinoday.cinema.cinema.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
public class PlaceId implements Serializable {
    private int place;
    private int row;
}
