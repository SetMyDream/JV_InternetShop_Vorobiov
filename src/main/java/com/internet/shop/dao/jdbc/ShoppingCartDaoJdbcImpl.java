package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
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
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String selectQuery =
                "SELECT * FROM shopping_carts WHERE user_id = ? AND deleted = FALSE ";
        ShoppingCart cart = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cart = new ShoppingCart(resultSet.getLong("cart_id"),
                        resultSet.getLong("user_id"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping_cart with userId: " + userId, e);
        }
        if (cart != null) {
            cart.setProducts(getShoppingCartProducts(cart.getShoppingCartId()));
        }
        return Optional.ofNullable(cart);
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement =
                connection.prepareStatement("INSERT INTO shopping_carts (user_id) VALUES(?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, shoppingCart.getUserId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                shoppingCart.setShoppingCartId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create ShoppingCart with id: "
                    + shoppingCart.getShoppingCartId(), e);
        }
        for (Product product : shoppingCart.getProducts()) {
            addDataToShoppingCartProductsTable(shoppingCart.getShoppingCartId(), product.getProductId());
        }
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getById(Long id) {
        String selectQuery =
                "SELECT * FROM shopping_carts WHERE shopping_carts_id = ? AND deleted = FALSE";
        ShoppingCart cart = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cart = new ShoppingCart(resultSet.getLong("cart_id"),
                        resultSet.getLong("user_id"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping_cart with id: " + id, e);
        }
        if (cart != null) {
            cart.setProducts(getShoppingCartProducts(cart.getShoppingCartId()));
        }
        return Optional.ofNullable(cart);
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        String deleteQuery = "DELETE FROM shopping_cart_products WHERE shopping_cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, cart.getShoppingCartId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update ShoppingCart with id: "
                    + cart.getShoppingCartId(), e);
        }
        return addProductsToShoppingCart(cart);
    }

    @Override
    public boolean delete(Long id) {
        String updateQuery = "UPDATE shopping_carts SET deleted = TRUE WHERE shopping_carts_id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement =
                connection.prepareStatement(updateQuery)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add products to shoppingCart", e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String selectQuery = "SELECT * FROM shopping_carts WHERE deleted = FALSE";
        List<ShoppingCart> carts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart cart = new ShoppingCart(resultSet.getLong("shopping_carts_id"),
                        resultSet.getLong("user_id"));
                carts.add(cart);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping_carts", e);
        }
        for (ShoppingCart cart : carts) {
            cart.setProducts(getShoppingCartProducts(cart.getShoppingCartId()));
        }
        return carts;
    }

    private void addDataToShoppingCartProductsTable(long cartId, long productId) {
        String insertQuery = "INSERT INTO shopping_cart_products "
                + "(shopping_cart_id, product_id) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(insertQuery)) {
            statement.setLong(1, cartId);
            statement.setLong(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add data to "
                    + "shopping_cart_products table", e);
        }
    }

    private List<Product> getShoppingCartProducts(long cartId) {
        String selectQuery = "SELECT * FROM products p JOIN shopping_cart_products scp "
                + "ON scp.product_id = p.product_id WHERE shopping_cart_id = ? AND deleted = FALSE";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getLong("product_id"),
                        resultSet.getBoolean("deleted")));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get ShoppingCart Products!!!", e);
        }
    }

    private ShoppingCart addProductsToShoppingCart(ShoppingCart cart) {
        String insertQuery = "INSERT INTO shopping_cart_products "
                + "(shopping_cart_id, product_id) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement =
                connection.prepareStatement(insertQuery)) {
            for (Product product : cart.getProducts()) {
                statement.setLong(1, cart.getShoppingCartId());
                statement.setLong(2, product.getProductId());
                statement.executeUpdate();
            }
            return cart;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add products to shoppingCart", e);
        }
    }
}
