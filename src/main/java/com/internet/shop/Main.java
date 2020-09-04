package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class Main {
   private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product iphone = new Product("Iphone 11", 1200);
        Product iphoneX = new Product("Iphone X", 1000);
        Product nokia5130 = new Product("Nokia 5130 Xpress Music", 100);

        productService.create(iphone);
        productService.create(iphoneX);
        productService.create(nokia5130);

        Product nokia5310 = new Product("Nokia 5310 Xpress Music", 110);

        nokia5310.setId(nokia5130.getId());
        System.out.println(productService.getById(3l).getName());

        productService.update(nokia5310);
        System.out.println(productService.getById(3L).getName());

        productService.delete(iphone.getId());
        iphoneX.setPrice(950);
        productService.update(iphoneX);
        System.out.println(productService.getAll());
    }
}
