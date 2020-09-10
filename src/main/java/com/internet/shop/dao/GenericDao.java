package com.internet.shop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T order);

    Optional<T> getById(K id);

    T update(T order);

    boolean delete(K id);

    List<T> getAll();
}
