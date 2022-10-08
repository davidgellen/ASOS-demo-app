package com.example.ReservationSystem.repository;

import com.example.ReservationSystem.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
