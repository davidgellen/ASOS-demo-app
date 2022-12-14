package com.example.ReservationSystem.cotroller;

import com.example.ReservationSystem.domain.dto.SeatDTO;
import com.example.ReservationSystem.domain.inputdto.SeatCreateInputDTO;
import com.example.ReservationSystem.exception.SeatNotFoundException;
import com.example.ReservationSystem.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/seats")
public class SeatController {

    private final SeatService seatService;

    @GetMapping("")
    public ResponseEntity<List<SeatDTO>> findAll() {
        try {
            List<SeatDTO> seats = seatService.findAllDto();
            return ResponseEntity.ok(seats);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDTO> findById(@PathVariable Long id) {
        try {
            SeatDTO seat = seatService.findDtoById(id);
            return ResponseEntity.ok(seat);
        } catch (SeatNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("")
    public ResponseEntity<SeatDTO> create(@RequestBody SeatCreateInputDTO inputDto) {
        try {
            SeatDTO seat = seatService.create(inputDto);
            return ResponseEntity.ok(seat);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<SeatDTO> update(@PathVariable Long id, @RequestBody SeatCreateInputDTO inputDto) {
        try {
            SeatDTO seat = seatService.update(id, inputDto);
            return ResponseEntity.ok(seat);
        } catch (SeatNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SeatDTO> delete(@PathVariable Long id) {
        try {
            seatService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("/{amount}")
    public ResponseEntity<?> createMultiple(@PathVariable Long amount) {
        try {
            seatService.generateMultiple(amount);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
