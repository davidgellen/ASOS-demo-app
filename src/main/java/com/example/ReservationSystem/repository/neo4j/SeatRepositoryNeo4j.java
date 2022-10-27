package com.example.ReservationSystem.repository.neo4j;

import com.example.ReservationSystem.domain.entity.neo4j.SeatNeo4j;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SeatRepositoryNeo4j extends Neo4jRepository<SeatNeo4j, Long> {
}
