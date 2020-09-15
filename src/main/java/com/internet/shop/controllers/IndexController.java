package com.internet.shop.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class IndexController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zonedDateTime = ZonedDateTime
                .of(localDate, localTime, ZoneId.of("Europe/Helsinki"));
        String time = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                        .format(zonedDateTime);
        req.setAttribute("time", time);
        req.setAttribute("message", "Hello World !!! Current time is ");
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }
}
