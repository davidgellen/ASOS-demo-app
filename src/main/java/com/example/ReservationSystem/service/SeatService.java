package com.example.ReservationSystem.service;

import com.example.ReservationSystem.domain.dto.SeatDTO;
import com.example.ReservationSystem.domain.entity.Reservation;
import com.example.ReservationSystem.domain.entity.Seat;
import com.example.ReservationSystem.domain.inputdto.SeatCreateInputDTO;
import com.example.ReservationSystem.exception.SeatNotFoundException;
import com.example.ReservationSystem.mapper.SeatMapper;
import com.example.ReservationSystem.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    @Lazy
    private ReservationService reservationService;

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

    public void generateMultiple(Long amount) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            seats.add(generateSeat());
        }
        seatRepository.saveAll(seats);
    }

    private Seat generateSeat() {
        Seat seat = new Seat(); // because we don't like DP and lombok' s builder
        seat.setCoordinatesX(generateRandomNumber(1000));
        seat.setCoordinatesY(generateRandomNumber(1000));
        return seat;
    }

    private Long generateRandomNumber(Integer start) {
        return (long) new Random().nextInt(start);
    }

    public Integer getTotalAmount() {
        return seatRepository.getTotalAmount();
    }

    public SeatDTO update(Long id, SeatCreateInputDTO inputDto) throws SeatNotFoundException {
        Seat employee = this.findById(id);
        seatMapper.update(employee, inputDto);
        seatRepository.save(employee);
        return seatMapper.toDto(employee);
    }

    public void deleteById(Long id) {
        List<Long> reservations = reservationService.getAllBySeatId(id)
                .stream().map(Reservation::getId).toList();
        reservationService.deleteByIds(reservations);
        seatRepository.deleteById(id);
    }

    public List<Seat> findAllWIthReservationId(Long id) {
        return seatRepository.findByReservations_Id(id);
    }

    public void saveAll(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }
}
