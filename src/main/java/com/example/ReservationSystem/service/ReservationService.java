package com.example.ReservationSystem.service;

import com.example.ReservationSystem.domain.dto.ReservationDTO;
import com.example.ReservationSystem.domain.entity.Employee;
import com.example.ReservationSystem.domain.entity.Reservation;
import com.example.ReservationSystem.domain.entity.Seat;
import com.example.ReservationSystem.domain.inputdto.ReservationCreateInputDTO;
import com.example.ReservationSystem.exception.ReservationNotFoundException;
import com.example.ReservationSystem.mapper.ReservationMapper;
import com.example.ReservationSystem.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    @Lazy
    private SeatService seatService;
    private final EmployeeService employeeService;

    @Autowired
    protected ReservationMapper reservationMapper;

    public Reservation findById(Long id) throws ReservationNotFoundException {
        return reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public ReservationDTO findDtoById(Long id) throws ReservationNotFoundException {
        return reservationMapper.toDto(this.findById(id));
    }

    public List<Reservation> findAllByIds(List<Long> ids) {
        return reservationRepository.findAllById(ids);
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

    public void generateMultiple(Long amount) {
        Integer seatAmount = seatService.getTotalAmount();
        Integer employeeAmount = employeeService.getTotalAmount();
        List<Reservation> reservations = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Reservation reservation = new Reservation();
            reservation.setEmployee(employeeService.findById((long) new Random().nextInt(employeeAmount - 1) + 1));
            reservation.setCreatedBy(employeeService.findById((long) new Random().nextInt(employeeAmount - 1) + 1));
            reservation.setSeat(seatService.findById((long) (new Random().nextInt(seatAmount - 1) + 1)));
            reservation.setStartTime(generateRandomDate());
            reservation.setEndTime(generateRandomDate());
            reservations.add(reservation);
        }
        reservationRepository.saveAll(reservations);
    }

    private LocalDateTime generateRandomDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay).atTime(new Random().nextInt(23), new Random().nextInt(59));
    }

    public ReservationDTO update(Long id, ReservationCreateInputDTO inputDto) throws ReservationNotFoundException {
        Reservation reservation = this.findById(id);
        reservationMapper.update(reservation, inputDto);
        reservationRepository.save(reservation);
        return reservationMapper.toDto(reservation);
    }

    public void deleteById(Long id) throws ReservationNotFoundException {
        Reservation reservation = this.findById(id);

        List<Employee> employees = employeeService.findAllWIthReservationId(id);
        employees.forEach(employee -> {
            employee.getReservations().remove(reservation);
        });
        employeeService.saveAll(employees);

        List<Seat> seats = seatService.findAllWIthReservationId(id);
        seats.forEach(seat -> {
            seat.getReservations().remove(reservation);
        });
        seatService.saveAll(seats);

        reservationRepository.deleteById(id);
    }

    public List<Reservation> getAllBySeatId(Long seatId) {
        return reservationRepository.findBySeat_Id(seatId);
    }

    public List<Reservation> getAllByEmployeeId(Long employeeId) {
        return reservationRepository.findByEmployee_Id(employeeId);
    }

    public void deleteByIds(List<Long> ids) {
        reservationRepository.deleteAllById(ids);
    }

}
