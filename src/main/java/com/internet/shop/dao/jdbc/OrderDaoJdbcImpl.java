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
    public Order create(Order order) {
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement =
                connection.prepareStatement("INSERT INTO orders (user_id) VALUES(?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setOrderId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create Order with id: "
                    + order.getOrderId(), e);
        }
        for (Product product : order.getProducts()) {
            addDataToOrdersProductTable(order.getOrderId(), product.getProductId());
        }
        return order;
    }

    @Override
    public Optional<Order> getById(Long id) {
        Order order = null;
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement =
                connection.prepareStatement("SELECT order_id, user_id FROM orders "
                        + "WHERE order_id = ? AND is_deleted = FALSE ")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = new Order(resultSet.getLong("order_id"),
                        resultSet.getLong("user_id"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get Order with id: " + id, e);
        }
        order.setProducts(getOrdersProducts(order.getOrderId()));
        return Optional.of(order);
    }

    @Override
    public Order update(Order order) {
        String query = "UPDATE orders SET user_id = ? "
                + "WHERE order_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getOrderId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update order with id "
                    + order.getOrderId(), e);
        }
        deleteOrderProducts(order.getOrderId());
        return order;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE orders "
                        + "SET is_deleted = TRUE WHERE order_id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete Order with id: " + id, e);
        }
        return deleteOrderProducts(id);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM orders WHERE is_deleted = FALSE")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getLong("order_id"),
                        resultSet.getLong("user_id")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add data to Orders_Product", e);
        }
        setProductsToOrders(orders);
        return orders;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        String query = "SELECT * FROM orders WHERE user_id = ? AND deleted = false;";
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                orderList.add(new Order(orderId, userId));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get orders of user with id" + userId, e);
        }
        for (Order order : orderList) {
            List<Product> productList = getOrdersProducts(order.getOrderId());
            order.setProducts(productList);
        }
        return orderList;
    }

    @Override
    public Optional<Order> getByUserId(Long id) {
        return Optional.empty();
    }

    private void addDataToOrdersProductTable(Long orderId, Long productId) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO "
                             + "orders_products (order_id, product_id) VALUES(?,?)")) {
            statement.setLong(1, orderId);
            statement.setLong(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add data to Orders_Product", e);
        }
    }

    private List<Product> getOrdersProducts(long orderId) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM products p JOIN "
                        + "orders_products op ON op.product_id = p.product_id "
                        + "WHERE order_id = ? AND is_deleted = FALSE")) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getLong("product_id"),
                        resultSet.getBoolean("deleted")));
            }

            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get Order Products", e);
        }
    }

    public boolean deleteOrderProducts(Long orderId) {
        String deleteQuery = "DELETE FROM orders_products WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, orderId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete Order with id: " + orderId, e);
        }
    }

    private void setProductsToOrders(List<Order> orders) {
        for (Order order : orders) {
            order.setProducts(getOrdersProducts(order.getOrderId()));
        }
    }
}
