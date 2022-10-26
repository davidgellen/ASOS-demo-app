package com.example.ReservationSystem.service;

import com.example.ReservationSystem.domain.dto.EmployeeDTO;
import com.example.ReservationSystem.domain.entity.Employee;
import com.example.ReservationSystem.domain.entity.Reservation;
import com.example.ReservationSystem.domain.inputdto.EmployeeCreateInputDTO;
import com.example.ReservationSystem.exception.EmployeeNotFoundException;
import com.example.ReservationSystem.mapper.EmployeeMapper;
import com.example.ReservationSystem.repository.EmployeeRepository;
import com.example.ReservationSystem.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RedisService redisService;
//    @Lazy

    @Autowired
    @Lazy
    private ReservationService reservationService;

    @Autowired
    protected EmployeeMapper employeeMapper;

    public Employee findById(Long id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public String findByIdRedis(Long id){
        return redisService.findEmployeeById(id).toString();
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

    public Set<Map<String, String>> findAllRedis(){
        return redisService.findEmployeesAll();
    }

    public EmployeeDTO create(EmployeeCreateInputDTO inputDTO) {
        Employee employee = employeeMapper.toEmployee(inputDTO);
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    public String createRedis(EmployeeCreateInputDTO employee){
        return redisService.createEmployee(employee);
    }

    public void generateMultiple(Long amount) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            employees.add(generateEmployee());
        }
        List<Employee> employeesCreated = employeeRepository.saveAll(employees);
    }

    private Employee generateEmployee() {
        Employee employee = new Employee();
        employee.setFirstname(generateRandomString(8));
        employee.setSurname(generateRandomString(12));
        employee.setLogin(generateRandomString(6));
        employee.setPassword(generateRandomString(15));
        return employee;
    }

    private String generateRandomString(Integer targetStringLength) {
        return new Random().ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public Integer getTotalAmount() {
        return employeeRepository.getTotalAmount();
    }

    public EmployeeDTO update(Long id, EmployeeCreateInputDTO inputDto) throws EmployeeNotFoundException {
        Employee employee = this.findById(id);
        employeeMapper.update(employee, inputDto);
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    public String updateRedis(Long id, EmployeeCreateInputDTO inputDto){
        return redisService.updateEmployee(id, inputDto);
    }

    public void deleteById(Long id) {
        List<Long> reservations = reservationService.getAllByEmployeeId(id)
                .stream().map(Reservation::getId).toList();
        reservationService.deleteByIds(reservations);
        employeeRepository.deleteById(id);
    }

    public Long deleteByIdRedis(Long id){
        return redisService.deleteEmployee(id);
    }

}
