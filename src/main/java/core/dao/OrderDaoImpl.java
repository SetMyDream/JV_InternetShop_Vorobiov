package core.dao;

import core.db.Storage;
import core.model.Order;

import java.util.Optional;
import java.util.stream.IntStream;

public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Optional<Order> getById(Long orderId) {
        return Storage.orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst();
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                .filter(i -> Storage.orders.get(i).getId().equals(order.getId()))
                .forEach(i -> Storage.orders.set(i, order));
        return order;
    }

    @Override
    public boolean deleteById(Long orderId) {
        return Storage.orders.removeIf(ord -> ord.getId().equals(orderId));
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }

    @Override
    public java.util.List<Order> getAllOrders() {
        return Storage.orders;
    }
}
