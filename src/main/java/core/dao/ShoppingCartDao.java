package core.dao;

import core.model.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getById(Long shoppingCartId);

    ShoppingCart update(ShoppingCart shoppingCart);

    boolean deleteById(Long shoppingCartId);

    boolean delete(ShoppingCart shoppingCart);

    List<ShoppingCart> getAllShoppingCarts();
}
