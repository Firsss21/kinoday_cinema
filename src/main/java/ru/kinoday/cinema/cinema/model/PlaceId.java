package ru.kinoday.cinema.cinema.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
@ToString
public class PlaceId implements Serializable {
    private int place;
    private int row;
}
