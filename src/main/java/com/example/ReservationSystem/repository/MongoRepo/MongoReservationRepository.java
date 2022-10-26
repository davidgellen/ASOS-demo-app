package com.example.ReservationSystem.repository.MongoRepo;

import com.example.ReservationSystem.domain.entity.mongo.MongoReservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MongoReservationRepository extends MongoRepository<MongoReservation, Long> {
    @Query("{name:'?0'}")
    MongoReservation findReservationByEmployeeId(Long id);

//    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
//    List<Reservation> findAll(String category);

//    public long count();
}
