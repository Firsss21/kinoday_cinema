package ru.kinoday.cinema.cinema.model;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private ScheduleElement scheduled;

    @OneToOne
    private Place place;

    @Enumerated(EnumType.STRING)
    private TicketType type;
}
