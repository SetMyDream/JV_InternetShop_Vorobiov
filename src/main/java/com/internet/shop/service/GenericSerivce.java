package com.internet.shop.service;

import java.util.List;

public interface GenericSerivce<T, V> {
    T get(V id);

    boolean delete(V id);

    List<T> getAll();
}
