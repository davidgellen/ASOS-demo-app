package com.example.ReservationSystem.repository;

import com.example.ReservationSystem.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query(value = "SELECT COUNT(*) FROM Seat", nativeQuery = true)
    Integer getTotalAmount();

    List<Seat> findByReservations_Id(Long id);

}
