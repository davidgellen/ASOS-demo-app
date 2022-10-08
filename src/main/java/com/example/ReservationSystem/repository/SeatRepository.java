package com.example.ReservationSystem.repository;

import com.example.ReservationSystem.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
