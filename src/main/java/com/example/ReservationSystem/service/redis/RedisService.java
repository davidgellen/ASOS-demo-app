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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
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

    private BufferedWriter getWriter(String filename){
        try{
            FileWriter fw = new FileWriter("C:\\Users\\zuzka\\Documents\\SCHOOL\\Ing-SEM4\\ASOS\\" + filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            return bw;
        } catch (Exception e){
            return null;
        }
    }

    private void write(BufferedWriter bw, String value){
        try{
            bw.write(String.valueOf(value));
            bw.newLine();
            bw.close();
        } catch (Exception e){
            return;
        }
    }

    public String generateRandomString(Integer targetStringLength) {
        return new Random().ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public LocalDateTime getNowDateTime(){
        return LocalDateTime.now();
    }

    private Set<String> getAllIds(String entity){
        return syncCommands.smembers(entity+"All");
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
    private Map<String, String> findHashById(String id, String path){
        Long start;
        Long finish;
        Map<String, String> res = null;
        try{
            BufferedWriter bw = getWriter(path);
            start = System.currentTimeMillis();
            res = syncCommands.hgetall(id);
            finish = System.currentTimeMillis();
            write(bw, String.valueOf(finish - start));
        }catch (Exception ex){

        }
        return res;
    }

    private Set<Map<String, String>> findHashAll(String key){
        Set<Map<String, String>> all = new HashSet<>();
        Set<String> result = syncCommands.smembers(key);
        for (String e:
                result) {
            all.add(findHashById(e, ""));
        }
        return all;
    }

    public Map<String, String> findEmployeeById(Long id){
        Map<String, String> record
                = findHashById("E"+id.toString(), "employee_read.txt");
        return record;
    }

    public Map<String, String> findSeatById(Long id){
        Map<String, String> record
                = findHashById("S"+id.toString(), "seat_read.txt");
        return record;
    }

    public Map<String, Object> findReservationById(Long id){
        Map<String, Object> record
                = new HashMap(findHashById("R"+id.toString(), "reservation_read.txt"));
        record.replace("employee", findHashById(record.get("employee").toString(), "nic.txt"));
        record.replace("seat", findHashById(record.get("seat").toString(), "zasnic.txt"));
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
            r.replace("employee", findHashById(r.get("employee").toString(), ""));
            r.replace("seat", findHashById(r.get("seat").toString(), ""));
        }
        return all;
    }


    public Set<String> getReservationListById(String id){
        return syncCommands.smembers(id+"R") ;
    }

    public String createEmployee(EmployeeCreateInputDTO e) {
        Long start;
        Long finish;
        String id = "E"+getIdCounter("employees");
        String ret = "";
        Map<String, String> properties = new HashMap<>()
        {{
            put("id", id);
            put("firstname", e.getFirstname());
            put("surname", e.getSurname());
            put("login", e.getLogin());
            put("password", e.getPassword());
            put("reservations", id+"R");
        }};
        //meranie
        try{
            BufferedWriter bw = getWriter("employee_create.txt");
            start = System.currentTimeMillis();
            ret = syncCommands.hmset(id, properties);
            if(Objects.equals(ret, "OK")) {
                syncCommands.sadd("employeesAll", id);
                increaseIdCounter("employees");
            }
            finish = System.currentTimeMillis();
            write(bw, String.valueOf(finish-start));
        } catch (Exception ex){
            ret = ex.getMessage();
        }
        //meranie
        return ret;
    }

    public String createSeat(SeatCreateInputDTO s) {
        Long start;
        Long finish;
        String id = "S"+getIdCounter("seats");
        Map<String, String> properties = new HashMap<>()
        {{
            put("id", id);
            put("coordinatesX", s.getCoordinatesX().toString());
            put("coordinatesY", s.getCoordinatesY().toString());
            put("reservations", id+"R");
        }};
        try{
            BufferedWriter bw = getWriter("seat_create.txt");
            start = System.currentTimeMillis();
            String ret = syncCommands.hmset(id, properties);
            if(Objects.equals(ret, "OK")) {
                syncCommands.sadd("seatsAll", id);
                increaseIdCounter("seats");
            }
            finish = System.currentTimeMillis();
            write(bw, String.valueOf(finish-start));
        }catch (Exception ex){
            return "error";
        }
        return "OK";
    }

    public String createReservation(ReservationCreateInputDTO r){
        Long start;
        Long finish;
        String id = "R"+getIdCounter("reservations");
        if (findEmployeeById(r.getEmployeeId()).isEmpty() || findSeatById(r.getSeatId()).isEmpty()) {
            return "Error";
        }
        Map<String, String> properties = new HashMap<>()
        {{
            put("id", id);
            put("employee","E"+ r.getEmployeeId().toString());
            put("seat","S"+ r.getSeatId().toString());
            put("startTime", getNowDateTime().toString());
            put("endTime", getNowDateTime().plusDays(3L).toString());
        }};
        try{
            BufferedWriter bw = getWriter("reservation_create.txt");
            start = System.currentTimeMillis();
            String ret = syncCommands.hmset(id, properties);
            if(Objects.equals(ret, "OK")) {
                ret = syncCommands.sadd("reservationsAll", id).toString();
                ret = syncCommands.sadd("E"+r.getEmployeeId().toString()+"R", id).toString();
                ret = syncCommands.sadd("S"+r.getSeatId().toString()+"R", id).toString();
                increaseIdCounter("reservations");
            }
            finish = System.currentTimeMillis();
            write(bw, String.valueOf(finish-start));
        } catch (Exception ex){
            return "error";
        }
        return "OK";
    }

    public String updateEmployee(Long id, EmployeeCreateInputDTO inputDto){
        String employeeId = "E"+id.toString();
        Long start;
        Long finish;
        Map<String, String> properties = new HashMap<>()
        {{
            put("firstname", inputDto.getFirstname());
            put("surname", inputDto.getSurname());
            put("login", inputDto.getLogin());
            put("password", inputDto.getPassword());
        }};
        try{
            BufferedWriter bw = getWriter("employee_update.txt");
            start = System.currentTimeMillis();
            syncCommands.hmset(employeeId, properties);
            finish = System.currentTimeMillis();
            write(bw, String.valueOf(finish-start));
            return "OK";
        }catch (Exception e){
            return "error";
        }
    }

    public String updateSeat(Long id, SeatCreateInputDTO inputDTO){
        String seatId = "S"+id.toString();
        Long start;
        Long finish;
        Map<String, String> properties = new HashMap<>()
        {{
            put("coordinatesX", inputDTO.getCoordinatesX().toString());
            put("coordinatesY", inputDTO.getCoordinatesY().toString());
        }};
        try{
            BufferedWriter bw = getWriter("seat_update.txt");
            start = System.currentTimeMillis();
            syncCommands.hmset(seatId, properties);
            finish = System.currentTimeMillis();
            write(bw, String.valueOf(finish-start));
            return "OK";
        }catch (Exception e){
            return "error";
        }
    }

    public String updateReservation(Long id, ReservationCreateInputDTO inputDTO){
        Long start;
        Long finish;
        String reservationId = "R"+id.toString();
        String oldEmployeeId = findHashById(reservationId, "").get("employee");
        String oldSeatId = findHashById(reservationId, "").get("seat");
        Map<String, String> properties = new HashMap<>()
        {{
            put("employee", "E"+inputDTO.getEmployeeId().toString());
            put("seat", "S"+inputDTO.getSeatId().toString());
            put("startTime", inputDTO.getStartTime().toString());
            put("endTime", inputDTO.getEndTime().toString());
        }};
        try{
            BufferedWriter bw = getWriter("reservation_update.txt");
            start = System.currentTimeMillis();
            String ret = syncCommands.hmset(reservationId, properties);
            if(!oldEmployeeId.equals("E"+inputDTO.getEmployeeId())){
                syncCommands.smove(oldEmployeeId+"R", "E"+inputDTO.getEmployeeId().toString()+"R", reservationId);
            }
            if(!oldSeatId.equals("S"+inputDTO.getSeatId())){
                syncCommands.smove(oldSeatId+"R", "E"+inputDTO.getSeatId().toString()+"R", reservationId);
            }
            finish = System.currentTimeMillis();
            write(bw, String.valueOf(finish-start));
        }catch (Exception ex){
            return "error";
        }
        return "OK";
    }

    public Long deleteEmployee(Long id){
        String employeeId = "E"+id.toString();
        Set<String> listOfReservations = getReservationListById(employeeId);
        Long start;
        Long finish;
        try {
            BufferedWriter bw = getWriter("employee_delete.txt");
            start = System.currentTimeMillis();
            for (String r:
                    listOfReservations) {
                deleteReservation(Long.parseLong(r.replace("R", "")));
            }
            syncCommands.del(employeeId+"R");
            removeEntityFromAll("employeesAll", employeeId);
            syncCommands.del(employeeId);
            finish = System.currentTimeMillis();
            write(bw, String.valueOf(finish-start));
            return 1L;

        } catch (Exception ex){
            return 0L;
        }
    }

    public Long deleteSeat(Long id){
        Long start;
        Long finish;
        String seatId = "S"+id.toString();
        Set<String> listOfReservations = getReservationListById(seatId);
        try{
            BufferedWriter bw = getWriter("seat_delete.txt");
            start = System.currentTimeMillis();
            for (String r:
                    listOfReservations) {
                deleteReservation(Long.parseLong(r.replace("R", "")));
            }
            syncCommands.del(seatId+"R");
            removeEntityFromAll("seatsAll", seatId);
            syncCommands.del(seatId);
            finish = System.currentTimeMillis();
            write(bw, String.valueOf(finish-start));
            return 1L;
        }catch (Exception ex){
            return 0L;
        }
    }
    public Long deleteReservation(Long id){
        Long start;
        Long finish;
        String reservationId = "R"+id.toString();
        String employeeId = syncCommands.hget(reservationId, "employee");
        String seatId = syncCommands.hget(reservationId, "seat");

        try{
            BufferedWriter bw = getWriter("reservation_delete_1000.txt");
            start = System.currentTimeMillis();
            syncCommands.srem(employeeId+"R", reservationId);
            syncCommands.srem(seatId+"R", reservationId);

            syncCommands.del(reservationId);
            removeEntityFromAll("reservationsAll", reservationId);
            finish = System.currentTimeMillis();
            write(bw, String.valueOf(finish-start));
            return 1L;
        }catch (Exception e){
            return 0L;
        }
    }

    public void generateMultipleEmployee(Long amount){
        for (var i = 0; i < amount; i++ ){
            EmployeeCreateInputDTO e = new EmployeeCreateInputDTO(
                    generateRandomString(8), generateRandomString(5),
                    generateRandomString(5), generateRandomString(12)
            );
            createEmployee(e);
        }
    }

    public void generateMultipleSeat(Long amount){
        for (var i = 0; i < amount; i++ ){
            SeatCreateInputDTO s = new SeatCreateInputDTO(48L, 10L);
            createSeat(s);
        }
    }

    public Long getRandomEmployeeId(){
        return Long.parseLong(syncCommands.srandmember("employeesAll").replace("E",""));
    }

    public Long getRandomSeatId(){
        return Long.parseLong(syncCommands.srandmember("seatsAll").replace("S",""));
    }

    public void generateMultipleReservation(Long amount){
        Long randEmployeeId;
        Long randSeatId;
        for (var i = 0; i < amount; i++ ){
            randEmployeeId = getRandomEmployeeId();
            randSeatId = getRandomSeatId();
            ReservationCreateInputDTO r = new ReservationCreateInputDTO(
                    randEmployeeId, randSeatId, getNowDateTime(), getNowDateTime().plusDays(3));
            createReservation(r);
        }
    }

    public String deleteAllEmployee(){
        for (String id:
             syncCommands.smembers("employeesAll")) {
            deleteEmployee(Long.parseLong(id.replace("E","")));
        }
        return  "ok";
    }

    public String deleteAllSeat(){
        for (String id:
             syncCommands.smembers("seatsAll")) {
            deleteSeat(Long.parseLong(id.replace("S","")));
        }
        return  "ok";
    }

    public String deleteAllReservation(){
        for (String id:
             syncCommands.smembers("reservationsAll")) {
            deleteReservation(Long.parseLong(id.replace("R","")));
        }
        return  "ok";
    }
}
