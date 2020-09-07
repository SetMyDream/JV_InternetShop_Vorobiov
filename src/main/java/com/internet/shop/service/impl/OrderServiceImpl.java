package com.internet.shop.service.impl;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Order;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.service.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    OrderDao orderDao;

    @Inject
    ShoppingCartDao shoppingCartDao;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = orderDao.create(new Order(shoppingCart.getUserId()));
        order.setProducts(java.util.List.copyOf(shoppingCart.getProducts()));
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDao.getAllOrders();
    }

    @Override
    public Order get(Long id) {
        return orderDao.getById(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAllOrders();
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
