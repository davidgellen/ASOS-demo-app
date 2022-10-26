package com.example.ReservationSystem.repository.MongoRepo;

import com.example.ReservationSystem.domain.entity.mongo.MongoEmployee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MongoEmployeeRepository extends MongoRepository<MongoEmployee, Long> {
    @Query("{firstname:'?0'}")
    MongoEmployee findEmployeeByName(String firstname);

//    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
//    List<Employee> findAll(String category);

//    public long count();
}
