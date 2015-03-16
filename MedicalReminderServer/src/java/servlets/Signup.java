package servlets;

import beans.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserDao;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Signup extends HttpServlet {

    private int Status;
    private static final int SUCCESS = 1;
    private static final int EMAIL_ALREADY_EXISTS = 2;
    private static final int FAILED = 3;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userDao = UserDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get response writer
        PrintWriter out = response.getWriter();


            // create user object
            User user = new User();

            user.setName(request.getParameter("name"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            
            System.out.println(request.getParameter("name"));
            
            // insert user in db
            boolean inserted = userDao.insertUser(user);

            // Create new JSON Obejct
            JSONObject jsonObj = new JSONObject();

            if (inserted) {
                // set status to success
                Status = SUCCESS;
            } else {
                // set status to email already existed
                Status = EMAIL_ALREADY_EXISTS;
            }

            // add login status to json object
            jsonObj.put("status", Status);

            // make output of json object
            out.print(jsonObj);


    }

}
