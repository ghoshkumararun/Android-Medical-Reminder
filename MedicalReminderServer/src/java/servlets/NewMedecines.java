/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.Medicine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MedicineDao;
import model.UserDao;

/**
 *
 * @author Radwa
 */
public class NewMedecines extends HttpServlet {
 private int Status;
    private static final int SUCCESS = 1;
    private static final int EMAIL_ALREADY_EXISTS = 2;
    private static final int FAILED = 3;
    private UserDao userDao;
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      //  processRequest(request, response);
        Gson myGson = new Gson();
       
        String requestedJson = request.getParameter("data");
        String requestedEmail = request.getParameter("email");

        java.lang.reflect.Type myType = new TypeToken<Medicine[]>(){}.getType();
        
         Medicine [] addedMedecines = myGson.fromJson(requestedJson, myType);
         MedicineDao medecineConn = MedicineDao.getInstance();
        ArrayList <Medicine> med = new ArrayList<>();
        for(int i=0;i<addedMedecines.length;i++){
            med.add(addedMedecines[i]);
            System.out.println(addedMedecines[i].getName());
        }
         medecineConn.insertMedicines(med,requestedEmail);
         
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
