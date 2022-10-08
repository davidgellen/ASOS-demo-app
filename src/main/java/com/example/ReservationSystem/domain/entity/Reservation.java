package com.example.ReservationSystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "employee", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @ManyToOne()
    @JoinColumn(name = "seat", referencedColumnName = "id", nullable = false)
    private Seat seat;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    private Employee createdBy;

}
