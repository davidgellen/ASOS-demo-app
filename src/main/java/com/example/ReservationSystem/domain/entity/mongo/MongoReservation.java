package com.example.ReservationSystem.domain.entity.mongo;

import com.example.ReservationSystem.domain.entity.Employee;
import com.example.ReservationSystem.domain.entity.Seat;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document("reservation")
public class MongoReservation {

    @Id
    private Long id;


    private Employee employee;


    private Seat seat;


    private LocalDateTime startTime;


    private LocalDateTime endTime;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


    private Employee createdBy;
}
