package core.service;

import core.model.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);

    Order getById(Long orderId);

    Order update(Order order);

    boolean delete(Order order);

    boolean deleteById(Long orderId);

    List<Order> getAll();
}
