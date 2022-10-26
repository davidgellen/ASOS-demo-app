package com.example.ReservationSystem;

import com.example.ReservationSystem.domain.dto.EmployeeDTO;
import com.example.ReservationSystem.domain.entity.Employee;
import com.example.ReservationSystem.domain.entity.Reservation;
import com.example.ReservationSystem.domain.entity.mongo.MongoEmployee;
import com.example.ReservationSystem.domain.inputdto.EmployeeCreateInputDTO;
import com.example.ReservationSystem.exception.EmployeeNotFoundException;
import com.example.ReservationSystem.mapper.EmployeeMapper;
import com.example.ReservationSystem.repository.EmployeeRepository;
import com.example.ReservationSystem.repository.MongoRepo.MongoEmployeeRepository;
import com.example.ReservationSystem.repository.MongoRepo.MongoSeatRepository;
import com.example.ReservationSystem.repository.ReservationRepository;
import com.example.ReservationSystem.service.EmployeeService;
import com.example.ReservationSystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
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
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	ReservationService reservationService;

	@Autowired
	ReservationRepository reservationRepository;

	public static void main(String[] args) {
		SpringApplication.run(ReservationSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String firstname = "JpiGkt18";
		System.out.println("Getting item by name: " + firstname);
		MongoEmployee employee = employeeRepo.findEmployeeByName(firstname);
		System.out.println(employee.getFirstname());




		Long start;
		Long finish;
		Long timeElapsed;
		Employee zam;
		Float average = Float.valueOf(0);
		Random rd = new Random(); // creating Random object
		int[] arr = new int[50];
		int val;
		for (int i = 0; i < 50; i++) {
			val = rd.nextInt(49) + 1; // storing random integers in an array

			start = System.currentTimeMillis();
			//zam = sqlEmployeeService.findById(id);
			zam = employeeRepository.findById((long) val).orElseThrow(() -> new EmployeeNotFoundException(5L));
			finish = System.currentTimeMillis();
			timeElapsed = finish - start;
//			System.out.println("time elapsed");
//			System.out.println(timeElapsed);
			average = average + timeElapsed;
//			System.out.println(average); // printing each array element
		}
		average = average /50;
		System.out.println("Average is "); // printing each array element
		System.out.println(average);

		System.out.println("=====================================================================================");
		System.out.println("=====================================================================================");
		System.out.println("=====================================================================================");


		for (int i = 0; i < 50; i++) {
			zam = sqlEmployeeService.generateEmployee();
			start = System.currentTimeMillis();
			employeeRepository.save(zam);
			finish = System.currentTimeMillis();
			timeElapsed = finish - start;
//			System.out.println("time elapsed");
//			System.out.println(timeElapsed);
			average = average + timeElapsed;
//			System.out.println(average);
		}
		average = average /50;
		System.out.println("Average is "); // printing each array element
		System.out.println(average);

		System.out.println("=====================================================================================");
		System.out.println("=====================================================================================");
		System.out.println("=====================================================================================");

		EmployeeDTO zam2;

		for (int i = 0; i < 50; i++) {


			val = rd.nextInt(49) + 1;
			zam = sqlEmployeeService.findById((long) val);;
			zam2 = employeeMapper.toDto(sqlEmployeeService.generateEmployee());
			EmployeeCreateInputDTO inputZam = new EmployeeCreateInputDTO(zam2.getFirstname(), zam2.getSurname(), zam2.getLogin(), zam2.getPassword());
			employeeMapper.update(zam, inputZam);
			start = System.currentTimeMillis();
			employeeRepository.save(zam);
			finish = System.currentTimeMillis();
			timeElapsed = finish - start;
//			System.out.println("time elapsed");
//			System.out.println(timeElapsed);
			average = average + timeElapsed;
//			System.out.println(average);
		}
		average = average /50;
		System.out.println("Average is "); // printing each array element
		System.out.println(average);


		System.out.println("=====================================================================================");
		System.out.println("=====================================================================================");
		System.out.println("=====================================================================================");


		for (int i = 0; i < 50; i++) {
			val = rd.nextInt(49) + 1;
			zam = sqlEmployeeService.generateEmployee();
			start = System.currentTimeMillis();
			employeeRepository.save(zam);
			finish = System.currentTimeMillis();
			timeElapsed = finish - start;
//			System.out.println("time elapsed");
//			System.out.println(timeElapsed);
			average = average + timeElapsed;
//			System.out.println(average);

			List<Long> reservations = reservationService.getAllByEmployeeId((long) val)
					.stream().map(Reservation::getId).toList();
			start = System.currentTimeMillis();
			reservationRepository.deleteAllById(reservations);
			employeeRepository.deleteById((long) val);
			finish = System.currentTimeMillis();
			timeElapsed = finish - start;
//			System.out.println("time elapsed");
//			System.out.println(timeElapsed);
			average = average + timeElapsed;
//			System.out.println(average);
		}
		average = average /50;
		System.out.println("Average is "); // printing each array element
		System.out.println(average);

		System.out.println("=====================================================================================");
		System.out.println("NOSQL");
		System.out.println("=====================================================================================");






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
