package core.service;

import core.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart getById(Long shoppingCartId);

    ShoppingCart update(ShoppingCart shoppingCart);

    boolean delete(ShoppingCart shoppingCart);

    boolean deleteById(Long shoppingCartId);

    List<ShoppingCart> getAll();
}
