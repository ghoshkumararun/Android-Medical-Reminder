/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import beans.User;

/**
 *
 * @author Mohamed
 */
public class TestModel {
    
    public static void main(String[] args) {
        UserDao userDao = UserDao.getInstance();
//        User user = new User();
//        user.setEmail("m@yahoo.com");
//        user.setName("Mohamed");
//        user.setPassword("123456");
//        
//        userDao.insertUser(user);
        
        User user = userDao.findUserByEmail("m@yahoo.com");
        System.out.println(user.getName()+"\n"+user.getPassword());
        
    }
    
}
