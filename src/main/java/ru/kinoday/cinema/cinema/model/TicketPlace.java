package ru.kinoday.cinema.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPlace {
    private int price;
    private int row;
    private int place;
    private boolean canOrder;
}
