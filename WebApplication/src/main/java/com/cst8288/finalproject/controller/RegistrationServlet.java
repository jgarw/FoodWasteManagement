package com.cst8288.finalproject.controller;

import com.cst8288.finalproject.model.User;
import com.cst8288.finalproject.service.UserService;

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

    private RegistrationServlet userService = null;

    public RegistrationServlet() {
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
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setUserType(userType);
        //user.setIsSubscribe(isSubscribe);

        try {
            userService.create(user);

            HttpSession session = request.getSession();
            session.setAttribute("username", email);
            session.setAttribute("user", userService.findByEmail(email));
        } catch (ValidationException e) {
            request.setAttribute("errorMsg", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
            dispatcher.forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
