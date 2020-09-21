package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products ( name, price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            (statement).setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Optional<Product> getById(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        Product product = new Product(null, 0);
        try (Connection connection = ConnectionUtil.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return Optional.ofNullable(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't read result of statement", e);
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products;";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get product all products", e);
        }
        return products;
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update product with ID "
                    + product.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE products SET deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            int i = statement.executeUpdate();
            return i == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete product from ID " + id, e);
        }
    }

    public Product getProductFromResultSet(ResultSet resultSet)
            throws SQLException {
        long productId = resultSet.getLong("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        boolean deleted = resultSet.getBoolean("deleted");
        return new Product(name, price, productId, deleted);
    }
}
