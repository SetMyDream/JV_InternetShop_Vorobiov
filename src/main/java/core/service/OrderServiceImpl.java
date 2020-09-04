package core.service;

import core.dao.OrderDao;
import core.lib.Inject;
import core.lib.Service;
import core.model.Order;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Order getById(Long orderId) {
        return orderDao.getById(orderId).get();
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public boolean delete(Order order) {
        return orderDao.delete(order);
    }

    @Override
    public boolean deleteById(Long orderId) {
        return orderDao.deleteById(orderId);
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAllOrders();
    }
}
