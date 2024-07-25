package main.java.com.cst8288.finalproject.controller;

import main.java.com.cst8288.finalproject.model.User;
import main.java.com.cst8288.finalproject.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 
 */
public class RegistrationServlet {

    private UserService userService = null;

    public SignupServlet() {
        this.userService = new UserService();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String userType = request.getParameter("userType");
        //Boolean isSubscribe = "on".equalsIgnoreCase(request.getParameter("isSubscribe"));

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setTypeId(userType);
        //user.setIsSubscribe(isSubscribe);

        try {
            userService.create(user);

            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("user", userService.findByUsername(username));
        } catch (ValidationException e) {
            request.setAttribute("errorMsg", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
