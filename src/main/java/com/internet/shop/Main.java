package com.internet.shop;

import com.internet.shop.db.Storage;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;

public class Main {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product iphone = new Product("Iphone 11", 1200);
        Product iphoneX = new Product("Iphone X", 1000);
        Product nokia5130 = new Product("Nokia 5130 Xpress Music", 100);
        Product nokia5310 = new Product("Nokia 5310 Xpress Music", 110);
        Product samsung1 = new Product("Samsung 1", 150);
        Product samsung2 = new Product("Samsung 2", 120);
        Product samsungX = new Product("Samsung X", 500);
        Product beetBite = new Product("BeetBite", 10);

        productService.create(iphone);
        productService.create(iphoneX);
        productService.create(nokia5130);
        productService.create(samsung1);
        productService.create(samsung2);
        productService.create(samsungX);
        productService.create(beetBite);

        UserService userService = (UserService) injector.getInstance(UserService.class);
        User iphoneUser = new User("Serg","Serg","1111");
        userService.create(iphoneUser);
        User nokiaUser = new User("Gerg","Gerg","1112");
        userService.create(nokiaUser);
        User samsungUser = new User("Georg","Georg","1121");
        userService.create(samsungUser);
        User beetUser = new User("Scourge","Scource","1211");
        userService.create(beetUser);

        ShoppingCartService shoppingCartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        ShoppingCart sergShoppingCart = new ShoppingCart(iphoneUser.getId());
        shoppingCartService.addProduct(sergShoppingCart,iphone);
        shoppingCartService.addProduct(sergShoppingCart,iphoneX);

        ShoppingCart gergShoppingCart = new ShoppingCart(nokiaUser.getId());
        shoppingCartService.addProduct(gergShoppingCart,nokia5130);

        ShoppingCart georgShoppingCart = new ShoppingCart(samsungUser.getId());
        shoppingCartService.addProduct(georgShoppingCart,samsung1);
        shoppingCartService.addProduct(georgShoppingCart,samsung2);
        shoppingCartService.addProduct(georgShoppingCart,samsungX);

        ShoppingCart scourgeShoppingCart = new ShoppingCart(beetUser.getId());
        shoppingCartService.addProduct(scourgeShoppingCart,beetBite);

        Storage.shoppingCarts.forEach(System.out::println);

        System.out.println("Delete Samsung 1&2 from georg`s shopcart:");
        shoppingCartService.deleteProduct(georgShoppingCart, samsung1);
        shoppingCartService.deleteProduct(georgShoppingCart, samsung2);
        Storage.shoppingCarts.forEach(System.out::println);

        System.out.println("delete shopcart of Scourge");
        shoppingCartService.delete(scourgeShoppingCart);
        Storage.shoppingCarts.forEach(System.out::println);

        System.out.println("Remove everything from Georg`s cart");
        shoppingCartService.clear(georgShoppingCart);
        Storage.shoppingCarts.forEach(System.out::println);

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        System.out.println("complete order Serg");
        orderService.completeOrder(sergShoppingCart);
        Storage.orders.forEach(System.out::println);
        Storage.shoppingCarts.forEach(System.out::println);

        System.out.println("get samsungUser order by ID");
        System.out.println(iphoneUser.getId());
        System.out.println(orderService.get(iphoneUser.getId()));

        System.out.println("get All orders");
        orderService.getAll();
        Storage.orders.forEach(System.out::println);
    }
}
