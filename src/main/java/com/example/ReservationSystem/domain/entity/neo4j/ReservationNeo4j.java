package com.example.ReservationSystem.domain.entity.neo4j;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@RelationshipProperties
public class ReservationNeo4j {

    @Id @GeneratedValue
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @TargetNode
    private SeatNeo4j seatNeo4j;

    public ReservationNeo4j(Long id, LocalDateTime startTime, LocalDateTime endTime, SeatNeo4j seatNeo4j) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seatNeo4j = seatNeo4j;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public SeatNeo4j getSeatNeo4j() {
        return seatNeo4j;
    }

    public void setSeatNeo4j(SeatNeo4j seatNeo4j) {
        this.seatNeo4j = seatNeo4j;
    }
}
