package com.example.ReservationSystem.exception;

public class ReservationNotFoundException extends RuntimeException{

    public ReservationNotFoundException(Long id) {
        super(String.format("Reservation with id %d not found", id));
    }


}
