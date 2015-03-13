/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import beans.TimeDto;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Time;

/**
 *
 * @author Mohamed
 */
public class TimeDao {

    private static final TimeDao INSTANCE = new TimeDao();
    private final Connection connection;

    private TimeDao() {
        
        // initiate db connection
        connection = new DBConnection().getConnection();
    }

    public static TimeDao getInstance() {
        return INSTANCE;
    }

    public boolean insertTimes(List<TimeDto> times) {

        // prepare query
        String query = "INSERT INTO time VALUES(?,?,?)";

        // inserted indicator
        int insrtedRows[] = {};

        try {

            // create prepared statement
            PreparedStatement statement = connection.prepareStatement(query);

            // create batches
            for (TimeDto time : times) {

                statement.setInt(1, time.getMedicine_id());
                statement.setTime(2, Time.valueOf(time.getTake_time()));
                statement.setFloat(3, time.getDose());
                statement.addBatch();
            }
                
            // execute insert batches
            insrtedRows = statement.executeBatch();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (insrtedRows.length > 0);
    }
    
    public List<TimeDto> selectTimesByMedicineId(int MedicineId){
        
        // prepare query
        String query = "SELECT * FROM time WHERE medicine_id = ?";
        
        // create List of times
        List<TimeDto> times = new ArrayList<>();
        
        try {
            
            // create prepared statement
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, MedicineId);
            // excute query
            ResultSet result = statement.executeQuery();
            
            // make list of times out of  the result
            while(result.next()){
                
                TimeDto time = new TimeDto();
                time.setMedicine_id(MedicineId);
                time.setTake_time(result.getTime("take_time").toString());
                time.setDose(result.getFloat("dose"));
                times.add(time);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return times;
        
    }

}
