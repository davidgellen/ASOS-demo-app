package com.example.ReservationSystem;

import com.example.ReservationSystem.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class ReservationSystemApplication implements CommandLineRunner {

	private final RedisService redisService;

	public static void main(String[] args)  {
		SpringApplication.run(ReservationSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		redisService.connect("redis://localhost:6379/");
	}
}
