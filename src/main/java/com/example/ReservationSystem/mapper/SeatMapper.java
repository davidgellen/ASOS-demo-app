package com.example.ReservationSystem.mapper;

import com.example.ReservationSystem.domain.dto.SeatDTO;
import com.example.ReservationSystem.domain.entity.Seat;
import com.example.ReservationSystem.domain.inputdto.SeatCreateInputDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class SeatMapper {

    public abstract SeatDTO toDto(Seat seat);

    public abstract Seat toSeat(SeatCreateInputDTO seatCreateInputDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "reservations", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    public abstract void update(@MappingTarget Seat employee, SeatCreateInputDTO inputDto);
}
