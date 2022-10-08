package com.example.ReservationSystem.repository;

import com.example.ReservationSystem.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
