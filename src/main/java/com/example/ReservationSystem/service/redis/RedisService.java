package com.example.ReservationSystem.service.redis;

import com.example.ReservationSystem.domain.entity.Employee;
import com.example.ReservationSystem.domain.entity.Reservation;
import com.example.ReservationSystem.domain.entity.Seat;
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


    public Set<String> getReservationListById(Long id, String entity){
//        Set<String> reservations = new HashSet<>();
        if(Objects.equals(entity, "E") || Objects.equals(entity, "S")) {
            return syncCommands.smembers(entity+id.toString()+"R") ;
        }
        return null;
    }

    public String createEmployee(Employee e) {
        String id = "E"+e.getId().toString();
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
            ret = syncCommands.sadd("employeesAll", id).toString();
        }
        return ret;
    }

    public String createSeat(Seat s) {
        String id = "S"+s.getId().toString();
        Map<String, String> properties = new HashMap<>()
        {{
            put("id", id);
            put("coordinatesX", s.getCoordinatesX().toString());
            put("coordinatesY", s.getCoordinatesY().toString());
            put("reservations", id+"R");
        }};
        String ret = syncCommands.hmset(id, properties);
        if(Objects.equals(ret, "OK")) {
            ret = syncCommands.sadd("seatsAll", id).toString();
        }
        return ret;
    }

    public String createReservation(Reservation r){
        String id = "R"+r.getId().toString();
        Map<String, String> properties = new HashMap<>()
        {{
            put("id", id);
            put("employee","E"+ r.getEmployee().getId().toString());
            put("seat","S"+ r.getSeat().getId().toString());
        }};
        String ret = syncCommands.hmset(id, properties);
        if(Objects.equals(ret, "OK")) {
            ret = syncCommands.sadd("reservationsAll", id).toString();
            ret = syncCommands.sadd("E"+r.getEmployee().getId().toString()+"R", id).toString();
            ret = syncCommands.sadd("S"+r.getSeat().getId().toString()+"R", id).toString();
        }
        return ret;
    }



}
