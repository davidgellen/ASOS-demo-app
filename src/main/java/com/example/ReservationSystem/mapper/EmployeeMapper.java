package com.example.ReservationSystem.mapper;

import com.example.ReservationSystem.domain.dto.EmployeeDTO;
import com.example.ReservationSystem.domain.entity.Employee;
import com.example.ReservationSystem.domain.inputdto.EmployeeCreateInputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = EmployeeMapper.class)
public abstract class EmployeeMapper {

    public abstract EmployeeDTO toDto(Employee employee);

    public abstract Employee toEmployee(EmployeeCreateInputDTO employeeInputDTO);

}
