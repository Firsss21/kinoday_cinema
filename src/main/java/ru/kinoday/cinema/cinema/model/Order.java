package ru.kinoday.cinema.cinema.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private Long scheduleId;

    private List<Place> tickets;

    boolean agreementAccepted;
}
