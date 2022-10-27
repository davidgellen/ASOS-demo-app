package com.example.ReservationSystem.domain.entity.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.List;

public class EmployeeNeo4j {

    @Id
    private Long id;
    private String firstname;
    private String surname;
    private String login;
    private String password;

    @Relationship(type="SEATS", direction = Relationship.Direction.OUTGOING)
    private List<ReservationNeo4j> seat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ReservationNeo4j> getSeat() {
        return seat;
    }

    public void setSeat(List<ReservationNeo4j> seat) {
        this.seat = seat;
    }

    public EmployeeNeo4j(Long id, String firstname, String surname, String login, String password, List<ReservationNeo4j> seat) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "EmployeeNeo4j{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", seat=" + seat +
                '}';
    }
}
