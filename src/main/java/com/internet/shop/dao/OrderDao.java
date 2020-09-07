package com.internet.shop.dao;

import com.internet.shop.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    public Order create(Order order);

    public Optional<Order> getById(Long orderId);

    public Order update(Order order);

    public boolean delete(Long orderId);

    public List<Order> getAllOrders();

    List<Order> getUserOrders(Long userId);
}
