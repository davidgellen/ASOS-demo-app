package com.example.ReservationSystem.repository.neo4j;

import com.example.ReservationSystem.domain.entity.neo4j.ReservationNeo4j;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ReservationRepositoryNeo4j extends Neo4jRepository<ReservationNeo4j, Long> {
}
