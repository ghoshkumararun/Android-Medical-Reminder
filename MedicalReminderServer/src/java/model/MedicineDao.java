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
    public Medicine[] selectAllMedecines(String email){
        ArrayList<Medicine> medecineList = new ArrayList<>();
        try {
            String query =  "SELECT * FROM medicine WHERE user_email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
           ResultSet result = statement.executeQuery();
           while(result.next()){
               Medicine temp = new Medicine();
               temp.setId(result.getInt("id"));
               temp.setName(result.getString("name"));
               temp.setStart_date(result.getLong("start_date"));
               temp.setEnd_date(result.getLong("end_date"));
               temp.setRepetition(result.getString("repetition"));
               temp.setInstruction(result.getString("instruction"));
               temp.setType(result.getString("type"));
               temp.setUser_email(email);
               medecineList.add(temp);
           }

        } catch (SQLException ex) {
            
            Logger.getLogger(MedicineDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        Medicine [] medArray = medecineList.toArray(new Medicine[medecineList.size()]);
        return medArray;
        
    }

    public boolean insertMedicines(List<Medicine> medicines,String email) {

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
                String date = medicine.getStart_date()+"";
          //      statement.setDate(2, Date.valueOf(date));
          //      statement.setDate(3, Date.valueOf(medicine.getEnd_date()+""));
                statement.setString(4, medicine.getRepetition());
                statement.setString(5, medicine.getInstruction());
                statement.setString(6, medicine.getType());
                statement.setString(7, email);
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
