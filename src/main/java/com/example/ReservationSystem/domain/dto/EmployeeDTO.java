package com.example.ReservationSystem.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;
    private String firstname;
    private String surname;
    private String login;
    private String password;
    // private List<ReservationDTO> reservationIds;

}
