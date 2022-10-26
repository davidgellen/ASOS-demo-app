package com.example.ReservationSystem.repository.MongoRepo;

import com.example.ReservationSystem.domain.entity.mongo.MongoSeat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MongoSeatRepository extends MongoRepository<MongoSeat, Long> {
    @Query("{id:'?0'}")
    MongoSeat findSeatById(Long id);

//    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
//    List<Seat> findAll(String category);

//    public long count();
}
