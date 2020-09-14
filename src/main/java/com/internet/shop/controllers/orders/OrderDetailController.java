package com.internet.shop.controllers.orders;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.OrderService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/orders/details")
public class OrderDetailController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println(orderService.getUserOrders(1L));
        Long orderId = Long.valueOf(req.getParameter("id"));
        List<Product> products = orderService.get(orderId).getProducts();
        req.setAttribute("order", orderId);
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/orders/details.jsp").forward(req, resp);
    }
}
