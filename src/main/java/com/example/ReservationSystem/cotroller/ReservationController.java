package com.example.ReservationSystem.cotroller;

import com.example.ReservationSystem.domain.dto.ReservationDTO;
import com.example.ReservationSystem.domain.inputdto.ReservationCreateInputDTO;
import com.example.ReservationSystem.exception.ReservationNotFoundException;
import com.example.ReservationSystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("")
    public ResponseEntity<List<ReservationDTO>> findAll() {
        try {
            List<ReservationDTO> reservations = reservationService.findAllDto();
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> findById(@PathVariable Long id) {
        try {
            ReservationDTO reservation = reservationService.findDtoById(id);
            return ResponseEntity.ok(reservation);
        } catch (ReservationNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("")
    public ResponseEntity<ReservationDTO> create(@RequestBody ReservationCreateInputDTO inputDto) {
        try {
            ReservationDTO reservation = reservationService.create(inputDto);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationDTO> update(@PathVariable Long id, @RequestBody ReservationCreateInputDTO inputDto) {
        try {
            ReservationDTO reservation = reservationService.update(id, inputDto);
            return ResponseEntity.ok(reservation);
        } catch (ReservationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ReservationDTO> delete(@PathVariable Long id) {
        try {
            reservationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("/{amount}")
    public ResponseEntity<?> createMultiple(@PathVariable Long amount) {
        try {
            reservationService.generateMultiple(amount);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }


}
