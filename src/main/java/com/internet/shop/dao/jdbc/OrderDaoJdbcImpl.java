package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(Long userId) {
        List<Order> userOrders = new ArrayList<>();
        String query = "SELECT order_id, user_id FROM orders WHERE deleted = FALSE AND user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userOrders.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get orders for user id " + userId, e);
        }
        for (Order order : userOrders) {
            order.setProducts(getOrdersProducts(order.getId()));
        }
        return userOrders;
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders(user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order " + order.toString(), e);
        }
        createOrdersProducts(order);
        return order;
    }

    @Override
    public Optional<Order> getById(Long id) {
        Order order;
        String query = "SELECT order_id, user_id FROM orders WHERE deleted = FALSE "
                + "AND order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order with id " + id, e);
        }
        order.setProducts(getOrdersProducts(id));
        return Optional.of(order);
    }

    @Override
    public Order update(Order order) {
        String query = "UPDATE orders SET user_id = ? WHERE order_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update order with id " + order.getId(), e);
        }
        deleteOrdersProducts(order);
        createOrdersProducts(order);
        return order;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE orders SET deleted = TRUE WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with id " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id, user_id FROM orders WHERE deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = getOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders", e);
        }
        for (Order order : orders) {
            order.setProducts(getOrdersProducts(order.getId()));
        }
        return orders;
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        return new Order(id,userId);
    }

    private List<Product> getOrdersProducts(Long orderId) {
        String query = "SELECT p.product_id, p.name, p.price FROM orders_products op "
                + "JOIN products p on p.product_id = op.product_id AND p.deleted = FALSE "
                + "AND op.deleted = FALSE WHERE op.order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                products.add(new Product(productId, name, price));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get products for order id " + orderId, e);
        }
    }

    private void createOrdersProducts(Order order) {
        String query = "INSERT INTO orders_products(order_id, product_id) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            for (Product product : order.getProducts()) {
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create products for order id "
                    + order.getId(), e);
        }
    }

    private boolean deleteOrdersProducts(Order order) {
        String query = "UPDATE orders_products SET deleted = TRUE WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products for order id "
                    + order.getId(), e);
        }
    }
}
