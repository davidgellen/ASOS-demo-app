package com.example.ReservationSystem.domain.inputdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReservationCreateInputDTO {

    private Long employeeId;
    private Long seatId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
