package ru.kinoday.cinema.cinema.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.kinoday.cinema.cinema.model.dto.ScheduleElementDTO;
import ru.kinoday.cinema.cinema.model.dto.TicketDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
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

    @Column
    private String personalHashCode;

    @Column
    private String email;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added")
    private Date added;


    public Ticket(ScheduleElement scheduled, Place place, TicketType type, String personalHashCode, String email) {
        this.scheduled = scheduled;
        this.place = place;
        this.type = type;
        this.personalHashCode = personalHashCode;
        this.email = email;
    }

    public TicketDTO toDto() {
        return new TicketDTO(
            this.id,
            ScheduleElementDTO.of(this.scheduled),
            this.place,
            this.type,
            this.personalHashCode,
            this.email
        );
    }
}
