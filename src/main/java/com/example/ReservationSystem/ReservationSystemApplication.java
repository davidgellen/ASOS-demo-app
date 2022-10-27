package com.example.ReservationSystem;

import com.example.ReservationSystem.domain.entity.neo4j.EmployeeNeo4j;
import com.example.ReservationSystem.domain.entity.neo4j.SeatNeo4j;
import com.example.ReservationSystem.repository.EmployeeRepository;
import com.example.ReservationSystem.repository.neo4j.EmployeeRepositoryNeo4j;
import com.example.ReservationSystem.repository.neo4j.ReservationRepositoryNeo4j;
import com.example.ReservationSystem.repository.neo4j.SeatRepositoryNeo4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReservationSystemApplication implements CommandLineRunner {

	private final EmployeeRepositoryNeo4j employeeRepositoryNeo4j;
	private final SeatRepositoryNeo4j seatRepositoryNeo4j;
	private final ReservationRepositoryNeo4j reservationRepositoryNeo4j;

	private final EmployeeRepository employeeRepository;

	public ReservationSystemApplication(EmployeeRepositoryNeo4j employeeRepositoryNeo4j, SeatRepositoryNeo4j seatRepositoryNeo4j, ReservationRepositoryNeo4j reservationRepositoryNeo4j, EmployeeRepository employeeRepository) {
		this.employeeRepositoryNeo4j = employeeRepositoryNeo4j;
		this.seatRepositoryNeo4j = seatRepositoryNeo4j;
		this.reservationRepositoryNeo4j = reservationRepositoryNeo4j;
		this.employeeRepository = employeeRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(ReservationSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*for(EmployeeNeo4j employeeNeo4j : employeeRepositoryNeo4j.findAll()) {
			System.out.println(employeeNeo4j.toString());
		}
		for(SeatNeo4j seatNeo4j : seatRepositoryNeo4j.findAll()) {
			System.out.println(seatNeo4j.toString());
		}*/
		long start = System.currentTimeMillis();
		employeeRepository.findAll();
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;
		System.out.println(timeElapsed);
		/*for(ReservationNeo4j reservationNeo4j : reservationRepositoryNeo4j.findAll()) {
			System.out.println(reservationNeo4j.getId());
		}*/
	}
}
