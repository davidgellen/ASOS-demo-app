package com.example.ReservationSystem.service.redis;

import com.example.ReservationSystem.domain.inputdto.EmployeeCreateInputDTO;
import com.example.ReservationSystem.domain.inputdto.ReservationCreateInputDTO;
import com.example.ReservationSystem.domain.inputdto.SeatCreateInputDTO;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RedisService {

    private RedisClient redisClient;
    private StatefulRedisConnection<String, String> connection;
    private RedisCommands<String, String> syncCommands;
    private RedisAsyncCommands<String, String> asyncCommands;
    public void connect(String uri){
        redisClient = RedisClient.create(uri);
        connection = redisClient.connect();
        syncCommands = connection.sync();
        asyncCommands = connection.async();

        syncCommands.set("mykey", "Hello from Lettuce!");
        String value = syncCommands.get("mykey");
        System.out.println( value );
    }

    private Long getIdCounter(String entity){
        return Long.parseLong(syncCommands.get(entity+"IdCounter"));
    }

    private void increaseIdCounter(String entity){
        Long inc = getIdCounter(entity) + 1;
        syncCommands.set(entity+"IdCounter", inc.toString());
    }

    private Long removeEntityFromAll(String entity, String keyToRemove){
        return syncCommands.srem(entity, keyToRemove);
    }
    private Map<String, String> findHashById(String id){
        return syncCommands.hgetall(id);
    }

    private Set<Map<String, String>> findHashAll(String key){
        Set<Map<String, String>> all = new HashSet<>();
        Set<String> result = syncCommands.smembers(key);
        for (String e:
                result) {
            all.add(findHashById(e));
        }
        return all;
    }

    public Map<String, String> findEmployeeById(Long id){
        Map<String, String> record
                = findHashById("E"+id.toString());
        return record;
    }

    public Map<String, String> findSeatById(Long id){
        Map<String, String> record
                = findHashById("S"+id.toString());
        return record;
    }

    public Map<String, Object> findReservationById(Long id){
        Map<String, Object> record
                = new HashMap(findHashById("R"+id.toString()));
        record.replace("employee", findHashById(record.get("employee").toString()));
        record.replace("seat", findHashById(record.get("seat").toString()));
        return record;
    }

    public Set<Map<String, String>> findEmployeesAll(){
        return findHashAll("employeesAll");
    }

    public Set<Map<String, String>> findSeatsAll(){
        return findHashAll("seatsAll");
    }

    public Set<Map<String, Object>> findReservationsAll(){
        Set<Map<String, Object>> all = new HashSet(findHashAll("reservationsAll"));
        for (Map<String, Object> r:
                all) {
            r.replace("employee", findHashById(r.get("employee").toString()));
            r.replace("seat", findHashById(r.get("seat").toString()));
        }
        return all;
    }


    public Set<String> getReservationListById(String id){
        return syncCommands.smembers(id+"R") ;
    }

    public String createEmployee(EmployeeCreateInputDTO e) {
        String id = "E"+getIdCounter("employees");
        Map<String, String> properties = new HashMap<>()
        {{
            put("id", id);
            put("firstname", e.getFirstname());
            put("surname", e.getSurname());
            put("login", e.getLogin());
            put("password", e.getPassword());
            put("reservations", id+"R");
        }};
        String ret = syncCommands.hmset(id, properties);
        if(Objects.equals(ret, "OK")) {
            syncCommands.sadd("employeesAll", id);
            increaseIdCounter("employees");
        }
        return ret;
    }

    public String createSeat(SeatCreateInputDTO s) {
        String id = "S"+getIdCounter("seats");
        Map<String, String> properties = new HashMap<>()
        {{
            put("id", id);
            put("coordinatesX", s.getCoordinatesX().toString());
            put("coordinatesY", s.getCoordinatesY().toString());
            put("reservations", id+"R");
        }};
        String ret = syncCommands.hmset(id, properties);
        if(Objects.equals(ret, "OK")) {
            syncCommands.sadd("seatsAll", id);
            increaseIdCounter("seats");
        }
        return ret;
    }

    public String createReservation(ReservationCreateInputDTO r){
        String id = "R"+getIdCounter("seats");
        Map<String, String> properties = new HashMap<>()
        {{
            put("id", id);
            put("employee","E"+ r.getEmployeeId().toString());
            put("seat","S"+ r.getSeatId().toString());
        }};
        String ret = syncCommands.hmset(id, properties);
        if(Objects.equals(ret, "OK")) {
            ret = syncCommands.sadd("reservationsAll", id).toString();
            ret = syncCommands.sadd("E"+r.getEmployeeId().toString()+"R", id).toString();
            ret = syncCommands.sadd("S"+r.getSeatId().toString()+"R", id).toString();
        }
        return ret;
    }

    public String updateEmployee(Long id, EmployeeCreateInputDTO inputDto){
        String employeeId = "E"+id.toString();
        Map<String, String> properties = new HashMap<>()
        {{
            put("firstname", inputDto.getFirstname());
            put("surname", inputDto.getSurname());
            put("login", inputDto.getLogin());
            put("password", inputDto.getPassword());
        }};
        return syncCommands.hmset(employeeId, properties);
    }

    public String updateSeat(Long id, SeatCreateInputDTO inputDTO){
        String seatId = "S"+id.toString();
        Map<String, String> properties = new HashMap<>()
        {{
            put("coordinatesX", inputDTO.getCoordinatesX().toString());
            put("coordinatesY", inputDTO.getCoordinatesY().toString());
        }};
        return syncCommands.hmset(seatId, properties);
    }

    public String updateReservation(Long id, ReservationCreateInputDTO inputDTO){
        String reservationId = "R"+id.toString();
        String oldEmployeeId = findHashById(reservationId).get("employee");
        String oldSeatId = findHashById(reservationId).get("seat");
        Map<String, String> properties = new HashMap<>()
        {{
            put("employee", "E"+inputDTO.getEmployeeId().toString());
            put("seat", "S"+inputDTO.getSeatId().toString());
            put("startTime", inputDTO.getStartTime().toString());
            put("endTime", inputDTO.getEndTime().toString());
        }};
        String ret = syncCommands.hmset(reservationId, properties);
        if(!oldEmployeeId.equals("E"+inputDTO.getEmployeeId())){
            syncCommands.smove(oldEmployeeId+"R", "E"+inputDTO.getEmployeeId().toString()+"R", reservationId);
        }
        if(!oldSeatId.equals("S"+inputDTO.getSeatId())){
            syncCommands.smove(oldSeatId+"R", "E"+inputDTO.getSeatId().toString()+"R", reservationId);
        }
        return ret;
    }

    public Long deleteEmployee(Long id){
        String employeeId = "E"+id.toString();
        Set<String> listOfReservations = getReservationListById(employeeId);
        for (String r:
             listOfReservations) {
            deleteReservation(Long.parseLong(r.replace("R", "")));
        }
        syncCommands.del(employeeId+"R");
        removeEntityFromAll("employeesAll", employeeId);
        return syncCommands.del(employeeId);
    }

    public Long deleteSeat(Long id){
        String seatId = "S"+id.toString();
        Set<String> listOfReservations = getReservationListById(seatId);
        for (String r:
                listOfReservations) {
            deleteReservation(Long.parseLong(r.replace("R", "")));
        }
        syncCommands.del(seatId+"R");
        removeEntityFromAll("seatsAll", seatId);
        return syncCommands.del(seatId);
    }
    public Long deleteReservation(Long id){
        String reservationId = "R"+id.toString();
        String employeeId = syncCommands.hget(reservationId, "employee");
        String seatId = syncCommands.hget(reservationId, "seat");

        syncCommands.srem(employeeId+"R", reservationId);
        syncCommands.srem(seatId+"R", reservationId);

        syncCommands.del(reservationId);
        return removeEntityFromAll("reservationsAll", reservationId);
    }

}
