package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.lib.Dao;
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
public class ProductDaoJdbcImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products ( product_name, price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            (statement).setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("We can`t add product with id = " + product.getId(), e);
        }
        return product;
    }

    @Override
    public Optional<Product> getById(Long id) {
        String query = "SELECT * FROM products WHERE deleted = false AND product_id = ?";
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
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products WHERE deleted = FALSE;";
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
        String query = "UPDATE products SET product_name = ?, price = ? "
                + "WHERE deleted = FALSE AND product_id = ?";
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
        String query = "UPDATE products SET deleted = TRUE WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete product from ID " + id, e);
        }
    }

    public Product getProductFromResultSet(ResultSet resultSet)
            throws SQLException {
        long productId = resultSet.getLong("product_id");
        String name = resultSet.getString("product_name");
        double price = resultSet.getDouble("price");
        boolean deleted = resultSet.getBoolean("deleted");
        return new Product(name, price, productId, deleted);
    }
}
