package com.internet.shop.dao;

import com.internet.shop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartDao extends GeneralDao<ShoppingCart, Long> {
    ShoppingCart create(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getById(Long shoppingCartId);

    ShoppingCart update(ShoppingCart shoppingCart);

    boolean delete(Long shoppingCartId);

    List<ShoppingCart> getAll();
}
