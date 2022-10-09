package com.example.ReservationSystem.service;

import com.example.ReservationSystem.domain.dto.ReservationDTO;
import com.example.ReservationSystem.domain.entity.Reservation;
import com.example.ReservationSystem.domain.inputdto.ReservationCreateInputDTO;
import com.example.ReservationSystem.exception.ReservationNotFoundException;
import com.example.ReservationSystem.mapper.ReservationMapper;
import com.example.ReservationSystem.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    protected ReservationMapper reservationMapper;

    public Reservation findById(Long id) throws ReservationNotFoundException {
        return reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public ReservationDTO findDtoById(Long id) throws ReservationNotFoundException {
        return reservationMapper.toDto(this.findById(id));
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public List<ReservationDTO> findAllDto() {
        return this.findAll().stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    public ReservationDTO create(ReservationCreateInputDTO inputDTO) {
        Reservation reservation = reservationMapper.toReservation(inputDTO);
        reservationRepository.save(reservation);
        return reservationMapper.toDto(reservation);
    }
}
