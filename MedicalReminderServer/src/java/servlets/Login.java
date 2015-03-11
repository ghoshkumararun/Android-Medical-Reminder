package servlets;

import beans.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserDao;
import org.json.simple.JSONObject;

public class Login extends HttpServlet {

    private static final int LOGIN_SUCESS = 1;
    private static final int INVALIED_EMAIL_OR_PASSWORD = 2;
    private static final int LOGIN_FAILED = 3;
    private int loginStatus;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userDao = UserDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get email parameter
        String email = request.getParameter("email");

        // get password parameter
        String password = request.getParameter("password");

        // find user by Email
        User user = userDao.findUserByEmail(email);

        // check email existance && password equality
        if (user != null && user.getPassword().equals(password)) {

            loginStatus = 1;

        } else {
            loginStatus = 2;
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
