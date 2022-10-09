package com.example.ReservationSystem.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {

    private Long id;
    private Long coordinatesX;
    private Long coordinatesY;

}
