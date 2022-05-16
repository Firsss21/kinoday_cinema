package ru.kinoday.cinema.cinema.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rating")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class KinopoiskResponse{
    @XmlElement(name = "kp_rating", required = false)
    private float ratingKp;
    @XmlElement(name = "imdb_rating", required = false)
    private float ratingImdb;

    public static KinopoiskResponse empty() {
        return new KinopoiskResponse(0f, 0f);
    }
}
