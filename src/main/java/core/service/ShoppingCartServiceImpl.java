package core.service;

import core.dao.ShoppingCartDao;
import core.lib.Inject;
import core.lib.Service;
import core.model.ShoppingCart;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart getById(Long shoppingCartId) {
        return shoppingCartDao.getById(shoppingCartId).get();
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        return shoppingCartDao.update(shoppingCart);
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return shoppingCartDao.delete(shoppingCart);
    }

    @Override
    public boolean deleteById(Long shoppingCartId) {
        return shoppingCartDao.deleteById(shoppingCartId);
    }

    @Override
    public List<ShoppingCart> getAll() {
        return shoppingCartDao.getAllShoppingCarts();
    }
}
