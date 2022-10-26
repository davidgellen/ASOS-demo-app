package com.example.ReservationSystem.repository;

import com.example.ReservationSystem.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByEmployee_Id(Long id);

    List<Reservation> findBySeat_Id(Long id);

}
