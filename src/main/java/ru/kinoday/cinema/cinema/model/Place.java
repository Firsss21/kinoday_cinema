package ru.kinoday.cinema.cinema.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Place {
    @EmbeddedId
    private PlaceId placeId;

    public Place(int row, int place) {
        this.placeId = new PlaceId(place, row);
    }

    public int getRow() {
        return this.placeId.getRow();
    }

    public int getPlace() {
        return this.placeId.getPlace();
    }
}


