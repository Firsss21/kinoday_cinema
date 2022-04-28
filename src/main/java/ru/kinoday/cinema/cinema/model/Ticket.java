package ru.kinoday.cinema.cinema.model;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    ScheduleElement scheduled;

//    @Column
//    Place place;

//    @Column
//    TicketType type;
}
