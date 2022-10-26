package com.example.ReservationSystem;

import com.example.ReservationSystem.domain.entity.Employee;
import com.example.ReservationSystem.domain.entity.mongo.MongoEmployee;
import com.example.ReservationSystem.repository.MongoRepo.MongoEmployeeRepository;
import com.example.ReservationSystem.repository.MongoRepo.MongoSeatRepository;
import com.example.ReservationSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Random;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoRepositories

public class ReservationSystemApplication implements CommandLineRunner {
	@Autowired
	MongoEmployeeRepository employeeRepo;
	@Autowired
	MongoSeatRepository seatRepo;
	@Autowired
	EmployeeService sqlEmployeeService;



	public static void main(String[] args) {
		SpringApplication.run(ReservationSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String firstname = "JpiGkt18";
		System.out.println("Getting item by name: " + firstname);
		MongoEmployee employee = employeeRepo.findEmployeeByName(firstname);
		System.out.println(employee.getFirstname());


		Long id= Long.valueOf(2);
		Employee zam = sqlEmployeeService.findById(id);
		System.out.println(zam.getFirstname());

		Long start;
		Long finish;
		Long timeElapsed;
		Float average = Float.valueOf(0);
		Random rd = new Random(); // creating Random object
		int[] arr = new int[50];
		int val;
		for (int i = 0; i < arr.length; i++) {
			val = rd.nextInt(49) + 1; // storing random integers in an array
			id= Long.valueOf(val);
			start = System.currentTimeMillis();
			zam = sqlEmployeeService.findById(id);
			finish = System.currentTimeMillis();
			timeElapsed = finish - start;
			System.out.println("time elapsed");
			System.out.println(timeElapsed);
			average = average + timeElapsed;
			System.out.println(average); // printing each array element
		}
		average = average /50;
		System.out.println("Average is "); // printing each array element
		System.out.println(average);





		//findOne by id
		//findAllEmpl that have reservations on certain room
//		select *
//				from b
//		where type_id in (
//				select type_id
//				from a
//				where status = true
//		)

	}
}
