package com.example.ReservationSystem.domain.inputdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EmployeeCreateInputDTO {

    private String firstname;
    private String surname;

}
