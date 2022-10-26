package com.example.ReservationSystem.domain.entity.mongo;

import com.example.ReservationSystem.domain.entity.Reservation;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("seat")
public class MongoSeat {

    @Id
    private Long id;


    private Long coordinatesX;


    private Long coordinatesY;


    private List<Reservation> reservations = new ArrayList<>();


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
