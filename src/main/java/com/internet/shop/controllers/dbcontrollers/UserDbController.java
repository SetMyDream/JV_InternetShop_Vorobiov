package com.internet.shop.controllers.dbcontrollers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/database")
public class UserDbController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("passwrod", "2444666668888888");
        String url = "jdbc:mysql://localhost:3306/";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, dbProperties);
            System.out.println("Connection to DB established!");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }
}
