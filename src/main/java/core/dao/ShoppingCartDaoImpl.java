package core.dao;

import core.db.Storage;
import core.model.ShoppingCart;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getById(Long shoppingCartId) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getId().equals(shoppingCartId))
                .findFirst();
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0, Storage.shoppingCarts.size())
                .filter(shopCart -> Storage.shoppingCarts
                        .get(shopCart).getId().equals(shoppingCart.getId()))
                .forEach(shopCart -> Storage.shoppingCarts.set(shopCart, shoppingCart));
        return shoppingCart;
    }

    @Override
    public boolean deleteById(Long shoppingCartId) {
        return Storage.shoppingCarts.removeIf(shoppingCart -> shoppingCart
                .getId().equals(shoppingCartId));
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return Storage.shoppingCarts.remove(shoppingCart);
    }

    @Override
    public List<ShoppingCart> getAllShoppingCarts() {
        return Storage.shoppingCarts;
    }
}
