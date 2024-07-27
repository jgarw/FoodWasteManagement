package com.cst8288.finalproject.controller;

import com.cst8288.finalproject.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 
 */
@WebServlet("/RegisterServlet")
public class RegistrationServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAOImpl();

    public RegistrationServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String userType = request.getParameter("userType");
        //Boolean isSubscribe = "on".equalsIgnoreCase(request.getParameter("isSubscribe"));

        User user = new User(name, email, password, phone, userType);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setUserType(userType);
        //user.setIsSubscribe(isSubscribe);

        try {
            userDAO.insertUser(user);
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
            session.setAttribute("name", name);
        } catch (Exception e) {
            request.setAttribute("errorMsg", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/webapp/WEB-INF/views/register.jsp");
            dispatcher.forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/webapp/WEB-INF/views/register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
