package com.internet.shop.dao.impl;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.db.Storage;
import com.internet.shop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long id) {
        return getAll().stream()
                .filter(shoppingCart -> shoppingCart.getUserId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<ShoppingCart> getById(Long id) {
        return getAll().stream()
                .filter(shoppingCart -> shoppingCart.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean delete(Long id) {
        return Storage.shoppingCarts.removeIf(s -> s.getId().equals(id));
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0, Storage.shoppingCarts.size())
                .filter(i -> Storage.shoppingCarts.get(i).getId().equals(shoppingCart.getId()))
                .forEach(i -> Storage.shoppingCarts.set(i, shoppingCart));
        return shoppingCart;
    }
}
