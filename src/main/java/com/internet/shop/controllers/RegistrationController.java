package com.internet.shop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("pass");
        String passwordRepeated = req.getParameter("pass-repeat");

        if (password.equals(passwordRepeated)) {
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "Password and repeated passwords are mismatch!");
            req.setAttribute("usernameStorage", username);
            req.getRequestDispatcher("/WEB-INF/views/users/registration.jsp").forward(req, resp);
        }
        System.out.println(username + " " + password);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/users/registration.jsp").forward(req, resp);
    }
}
