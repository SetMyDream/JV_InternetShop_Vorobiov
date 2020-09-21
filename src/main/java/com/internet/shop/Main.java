package com.internet.shop;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import java.util.List;

public class Main {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    private static void readAll(ProductDao productDao) {
        List<Product> all = productDao.getAll();
        for (Product product : all) {
            System.out.println(product);
        }
    }

    public static void main(String[] args) {
        ProductDao productDao =
                (ProductDao) injector.getInstance(ProductDao.class);

        System.out.println("Сreate");
        Product iphone = new Product("Iphone 11", 1200);
        System.out.println(productDao.create(iphone));
        Product iphoneX = new Product("Iphone X", 1000);
        productDao.create(iphoneX);
        Product nokia5130 = new Product("Nokia 5130 Xpress Music", 100);
        productDao.create(nokia5130);

        System.out.println("Read");
        readAll(productDao);

        System.out.println("Update");
        iphoneX.setPrice(500);
        productDao.update(iphone);
        nokia5130.setName("Nokia 5310! Updated!");
        productDao.update(nokia5130);
        readAll(productDao);

        System.out.println("Delete");
        productDao.delete(iphone.getId());
        readAll(productDao);
    }
}
