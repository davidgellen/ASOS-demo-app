package com.example.ReservationSystem.service;

import com.example.ReservationSystem.domain.dto.SeatDTO;
import com.example.ReservationSystem.domain.entity.Seat;
import com.example.ReservationSystem.domain.inputdto.SeatCreateInputDTO;
import com.example.ReservationSystem.exception.SeatNotFoundException;
import com.example.ReservationSystem.mapper.SeatMapper;
import com.example.ReservationSystem.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    protected SeatMapper seatMapper;

    public Seat findById(Long id) throws SeatNotFoundException {
        return seatRepository.findById(id).orElseThrow(() -> new SeatNotFoundException(id));
    }

    public SeatDTO findDtoById(Long id) throws SeatNotFoundException {
        return seatMapper.toDto(this.findById(id));
    }

    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    public List<SeatDTO> findAllDto() {
        return this.findAll().stream()
                .map(seatMapper::toDto)
                .toList();
    }

    public SeatDTO create(SeatCreateInputDTO inputDTO) {
        Seat seat = seatMapper.toSeat(inputDTO);
        seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }
}
