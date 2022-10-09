package com.example.ReservationSystem.domain.inputdto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatCreateInputDTO {

    private Long coordinatesX;
    private Long coordinatesY;

}
