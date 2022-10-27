package com.example.ReservationSystem.domain.entity.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class SeatNeo4j {

    @Id
    private Long id;
    private Integer coordinatesX;
    private Integer coordinatesY;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(Integer coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public Integer getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(Integer coordinatesY) {
        this.coordinatesY = coordinatesY;
    }

    public SeatNeo4j(Long id, Integer coordinatesX, Integer coordinatesY) {
        this.id = id;
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
    }

    @Override
    public String toString() {
        return "SeatNeo4j{" +
                "id=" + id +
                ", coordinatesX=" + coordinatesX +
                ", coordinatesY=" + coordinatesY +
                '}';
    }
}
