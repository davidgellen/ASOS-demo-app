package com.example.ReservationSystem.repository.neo4j;

import com.example.ReservationSystem.domain.entity.neo4j.EmployeeNeo4j;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface EmployeeRepositoryNeo4j extends Neo4jRepository<EmployeeNeo4j, Long> {

}
