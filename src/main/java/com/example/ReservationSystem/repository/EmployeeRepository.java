package com.example.ReservationSystem.repository;

import com.example.ReservationSystem.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT COUNT(*) FROM Employee", nativeQuery = true)
    Integer getTotalAmount();

    List<Employee> findByReservations_Id(Long id);

}
