package com.example.ReservationSystem.mapper;

import com.example.ReservationSystem.domain.dto.ReservationDTO;
import com.example.ReservationSystem.domain.entity.Reservation;
import com.example.ReservationSystem.domain.inputdto.ReservationCreateInputDTO;
import com.example.ReservationSystem.service.EmployeeService;
import com.example.ReservationSystem.service.SeatService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ReservationMapper{

    @Autowired
    protected EmployeeService employeeService;

    @Autowired
    protected SeatService seatService;

    public abstract ReservationDTO toDto(Reservation reservation);

    @Mappings({
            @Mapping(expression = "java(employeeService.findById(createInputDTO.getEmployeeId()))",
                    target = "employee"),
            @Mapping(expression = "java(employeeService.findById(createInputDTO.getEmployeeId()))",
                    target = "createdBy"), // TODO: implement with spring security
            @Mapping(expression = "java(seatService.findById(createInputDTO.getSeatId()))",
                    target = "seat")
    })
    public abstract Reservation toReservation(ReservationCreateInputDTO createInputDTO);

}
