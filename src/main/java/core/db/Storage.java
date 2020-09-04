package core.db;

import java.util.ArrayList;
import java.util.List;

import core.model.Order;
import core.model.Product;
import core.model.ShoppingCart;
import core.model.User;

public class Storage {
    private static long productId;
    public static final List<Product> products = new ArrayList<>();
    private static long orderId;
    public static final List<Order> orders = new ArrayList<>();
    private static long userId;
    public static final List<User> users = new ArrayList<>();
    private static long shoppingCartId;
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();

    public static void addProduct(Product product) {
        productId++;
        product.setId(productId);
        products.add(product);
    }

    public static void addOrder(Order order){
        orderId++;
        order.setId(orderId);
        orders.add(order);
    }

    public static void addUser(User user){
        userId++;
        user.setId(userId);
        users.add(user);
    }

    public static void addShoppingCart(ShoppingCart shoppingCart){
        shoppingCartId++;
        shoppingCart.setId(shoppingCartId);
        shoppingCarts.add(shoppingCart);
    }

}
