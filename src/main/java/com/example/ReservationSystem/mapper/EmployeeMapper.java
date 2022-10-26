package com.example.ReservationSystem.mapper;

import com.example.ReservationSystem.domain.dto.EmployeeDTO;
import com.example.ReservationSystem.domain.entity.Employee;
import com.example.ReservationSystem.domain.inputdto.EmployeeCreateInputDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = EmployeeMapper.class)
public abstract class EmployeeMapper {

    public abstract EmployeeDTO toDto(Employee employee);

    public abstract Employee toEmployee(EmployeeCreateInputDTO employeeInputDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "reservations", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    public abstract void update(@MappingTarget Employee employee, EmployeeCreateInputDTO inputDto);
}
