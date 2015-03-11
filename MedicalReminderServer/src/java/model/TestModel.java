/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import beans.Medicine;
import beans.TimeDto;
import java.util.*;

/**
 *
 * @author Mohamed
 */
public class TestModel {
    
    public static void main(String[] args) {
        
        List<Medicine> meds = new ArrayList<>();
        
        Medicine med = new Medicine();
        med.setName("panadol Cold");
        med.setStart_date("2015-4-1");
        med.setEnd_date("2015-5-1");
        med.setRepetition("4d");
        med.setType("Capsol");
        med.setUser_email("m@yahoo.com");
        med.setInstruction("before Food");
        List<TimeDto> times = new ArrayList<>();
        TimeDto time = new TimeDto();
        time.setTake_time("6:00:00");
        time.setDose(3);
        times.add(time);
        time = new TimeDto();
        time.setTake_time("10:00:00");
        time.setDose(2.5f);
        times.add(time);
        med.setTimes(times);
        meds.add(med);
        
        med = new Medicine();
        med.setName("paramol");
        med.setStart_date("2015-5-1");
        med.setEnd_date("2015-6-1");
        med.setRepetition("4m");
        med.setType("injection");
        med.setUser_email("m@yahoo.com");
        med.setInstruction("after Food");
        times = new ArrayList<>();
        time = new TimeDto();
        time.setTake_time("11:00:00");
        time.setDose(3.7f);
        times.add(time);
        time = new TimeDto();
        time.setTake_time("21:00:00");
        time.setDose(.6f);
        times.add(time);
        med.setTimes(times);
        meds.add(med);
        
        MedicineDao.getInstance().insertMedicines(meds);
        
    }
    
}
