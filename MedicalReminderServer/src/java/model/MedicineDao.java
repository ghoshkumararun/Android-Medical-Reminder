/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import beans.Medicine;
import beans.TimeDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed
 */
public class MedicineDao {

    private static final MedicineDao INSTANCE = new MedicineDao();
    private final Connection connection;
    private TimeDao timeDao;

    private MedicineDao() {

        // initiate db connection
        connection = new DBConnection().getConnection();
        
        timeDao = TimeDao.getInstance();
    }
    

    public static MedicineDao getInstance() {
        return INSTANCE;
    }

    public boolean insertMedicines(List<Medicine> medicines) {

        // prepare query
        String query = "INSERT INTO medicine(name, start_date, end_date, repetition, instruction, type, user_email) VALUES(?,?,?,?,?,?,?)";

        // inserted indicator
        int insrtedRows[] = {};

        try {

            // create prepared statement
            PreparedStatement statement = connection.prepareStatement(query);

            // create batches
            for (Medicine medicine : medicines) {

                statement.setString(1, medicine.getName());
                statement.setDate(2, Date.valueOf(medicine.getStart_date()));
                statement.setDate(3, Date.valueOf(medicine.getEnd_date()));
                statement.setString(4, medicine.getRepetition());
                statement.setString(5, medicine.getInstruction());
                statement.setString(6, medicine.getType());
                statement.setString(7, medicine.getUser_email());
                statement.addBatch();

            }

            // execute insert batches
            insrtedRows = statement.executeBatch();
            
            if(insrtedRows.length > 0){
                
                for(Medicine medicine : medicines){
                    
                    // get id for select medicine
                    int medicine_id = selectIdByMedicineNameAndEmail(medicine.getName(), medicine.getUser_email());
                    
                    // add medicine id to every time dto of this medicine
                    for(TimeDto time: medicine.getTimes()){
                        time.setMedicine_id(medicine_id);
                    }
                    
                    // insert times of medicine in db
                    timeDao.insertTimes(medicine.getTimes());
                }
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (insrtedRows.length > 0);

    }

    private int selectIdByMedicineNameAndEmail(String name, String email) {

        // prepare query
        String query = "SELECT id FROM medicine WHERE name = ? AND user_email = ?";

        try {

            // create prepared statement
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);

            // excute query
            ResultSet result = statement.executeQuery();

            // check if there is rows in result
            if (result != null && result.next()) {

                return result.getInt("id");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

}
