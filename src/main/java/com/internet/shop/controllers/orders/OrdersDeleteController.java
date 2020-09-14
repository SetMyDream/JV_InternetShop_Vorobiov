package com.internet.shop.controllers.orders;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/orders/delete")
public class OrdersDeleteController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("id");
        Long id = Long.valueOf(userId);
        orderService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/orders/admin");
    }
}
