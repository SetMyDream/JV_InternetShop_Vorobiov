package core;

import core.db.Storage;
import core.model.Product;

public class Main {
    public static void main(String[] args) {
        Product iphone = new Product("Iphone 11", 1200);
        Product iphoneX = new Product("Iphone X", 1000);

        Storage.addProduct(iphone);
        Storage.addProduct(iphoneX);

        Storage.products.forEach(System.out::println);
    }
}
