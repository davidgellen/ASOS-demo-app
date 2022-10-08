package com.example.ReservationSystem.cotroller;

import com.example.ReservationSystem.domain.dto.EmployeeDTO;
import com.example.ReservationSystem.domain.inputdto.EmployeeCreateInputDTO;
import com.example.ReservationSystem.exception.EmployeeNotFoundException;
import com.example.ReservationSystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<List<EmployeeDTO>> findAll() {
        try {
            List<EmployeeDTO> employees = employeeService.findAllDto();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable Long id) {
        try {
            EmployeeDTO employee = employeeService.findDtoById(id);
            return ResponseEntity.ok(employee);
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("")
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeCreateInputDTO inputDto) {
        try {
            EmployeeDTO employee = employeeService.create(inputDto);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
