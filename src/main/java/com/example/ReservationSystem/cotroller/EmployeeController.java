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
import java.util.Map;
import java.util.Set;

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


    @GetMapping("/redis")
    public ResponseEntity<Set<Map<String, String>>> findAllRedis() {
        try {
            Set<Map<String, String>> employees = employeeService.findAllRedis();
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


    @GetMapping("/redis/{id}")
    public ResponseEntity<?> findByIdRedis(@PathVariable Long id) {
        try {
            String employee = employeeService.findByIdRedis(id);
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

    @PostMapping("/redis")
    public ResponseEntity<String> createRedis(@RequestBody EmployeeCreateInputDTO inputDto) {
        try {
            String employee = employeeService.createRedis(inputDto);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody EmployeeCreateInputDTO inputDto) {
        try {
            EmployeeDTO employee = employeeService.update(id, inputDto);
            return ResponseEntity.ok(employee);
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("/redis/{id}")
    public ResponseEntity<String> updateRedis(@PathVariable Long id, @RequestBody EmployeeCreateInputDTO inputDto) {
        try {
            String response = employeeService.updateRedis(id, inputDto);
            return ResponseEntity.ok(response);
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<EmployeeDTO> delete(@PathVariable Long id) {
        try {
            employeeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/redis/{id}")
    public ResponseEntity<Long> deleteRedis(@PathVariable Long id) {
        try {
            employeeService.deleteByIdRedis(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/redis")
    public ResponseEntity<?> deleteAllRedis(){
        try {
            employeeService.deleteAllRedis();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("/{amount}")
    public ResponseEntity<?> createMultiple(@PathVariable Long amount) {
        try {
            employeeService.generateMultiple(amount);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("/redis/{amount}")
    public ResponseEntity<?> createMultipleRedis(@PathVariable Long amount) {
        try {
            employeeService.generateMultipleRedis(amount);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }




}
