package com.example.ReservationSystem.service;

import com.example.ReservationSystem.domain.dto.EmployeeDTO;
import com.example.ReservationSystem.domain.entity.Employee;
import com.example.ReservationSystem.domain.inputdto.EmployeeCreateInputDTO;
import com.example.ReservationSystem.exception.EmployeeNotFoundException;
import com.example.ReservationSystem.mapper.EmployeeMapper;
import com.example.ReservationSystem.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

//    @Lazy
    @Autowired
    protected EmployeeMapper employeeMapper;

    public Employee findById(Long id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public EmployeeDTO findDtoById(Long id) throws EmployeeNotFoundException {
        return employeeMapper.toDto(this.findById(id));
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<EmployeeDTO> findAllDto() {
        return this.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    public EmployeeDTO create(EmployeeCreateInputDTO inputDTO) {
        Employee employee = employeeMapper.toEmployee(inputDTO);
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }
}
