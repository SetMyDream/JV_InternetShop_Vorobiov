package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);
    private final UserService userService =
            (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User alice = new User("Alice", "Madness", "returns");
        userService.create(alice);
        User john = new User("John", "Dou", "index");
        userService.create(john);
        User tod = new User("Tod", "Howard", "it`s just works");
        userService.create(tod);

        Product iphoneX = new Product("Iphone X", 1000);
        productService.create(iphoneX);
        Product nokiaXxx = new Product("Nokia XXX", 150);
        productService.create(nokiaXxx);
        Product samsungS9 = new Product("Samsung S9", 500);
        productService.create(samsungS9);

        ShoppingCart aliceShoppingCart = new ShoppingCart(alice.getId());
        shoppingCartService.create(aliceShoppingCart);
        ShoppingCart johnShoppingCart = new ShoppingCart(john.getId());
        shoppingCartService.create(johnShoppingCart);
        ShoppingCart todShoppingCart = new ShoppingCart(tod.getId());
        shoppingCartService.create(todShoppingCart);

        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
