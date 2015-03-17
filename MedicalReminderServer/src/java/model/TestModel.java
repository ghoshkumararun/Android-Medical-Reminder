/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import beans.Medicine;
import beans.TimeDto;
import java.util.*;
import java.sql.Time;

/**
 *
 * @author Mohamed
 */
public class TestModel {
    
    public static void main(String[] args) {
        
        String [] arr = {"one", "two    ", "three"};
        
        int pos = Arrays.binarySearch(arr, "two    ");
        System.out.println(""+pos);
    }
    
}
