package com.cst8288.finalproject.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cst8288.finalproject.model.FoodItem;
import com.cst8288.finalproject.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO loginDAO = new UserDAOImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }
	

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = loginDAO.authUser(email, password);
        boolean isValid = user != null && user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password);
        if (isValid) {
            HttpSession session = request.getSession();
            session.setAttribute("name", user.getName());
            session.setAttribute("user", user);

            FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
            List<FoodItem> items = foodItemsDAO.retriveAllFoodItems(user.getEmail());
            request.setAttribute("items", items);

        } else {
            request.setAttribute("errorMsg", "Username or password is invalid.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


}
