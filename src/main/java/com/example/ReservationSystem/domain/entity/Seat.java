package com.example.ReservationSystem.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "seat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coordinates_x")
    private Long coordinatesX;

    @Column(name = "coordinates_y")
    private Long coordinatesY;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seat")
    private List<Reservation> reservations = new ArrayList<>();

}
