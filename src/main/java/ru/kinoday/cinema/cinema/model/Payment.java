package ru.kinoday.cinema.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Payment {
    private Long uid;
    private Timestamp created;
    private Integer price;
    private Set<Long> ticketIds;
    private String email;
}
