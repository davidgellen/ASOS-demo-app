package com.example.ReservationSystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Long id;
    @JsonIgnoreProperties({"login", "password"})
    private EmployeeDTO employee;
    private SeatDTO seat;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
