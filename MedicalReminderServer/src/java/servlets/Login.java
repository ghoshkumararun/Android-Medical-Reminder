package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class Login extends HttpServlet {

    private static final int LOGIN_SUCESS = 1;
    private static final int INVALIED_EMAIL_OR_PASSWORD = 2;
    private static final int LOGIN_FAILED = 3;
    private int loginStatus;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // get email parameter
        String email = request.getParameter("email");
        
        // get password parameter
        String password  = request.getParameter("password");
        
        // check email existance
        if(email.equals("m@yahoo.com")){
            
            if(password.equals("123456")){
                loginStatus = 1;
            }else
                loginStatus = 2;
            
        }else{
            loginStatus =2; 
        }
        
        // Create JSON Obejct
        JSONObject jsonObj = new JSONObject();
        
        // add login status to json object
        jsonObj.put("status", loginStatus);
        
        // get response writer
        PrintWriter out = response.getWriter();
        
        // make output of json object
        out.print(jsonObj);
    }

 
}
