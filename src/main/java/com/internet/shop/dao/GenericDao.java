package com.internet.shop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    public T create(T object);

    public Optional<T> getById(K id);

    public T update(T object);

    public boolean delete(K id);

    public List<T> getAll();
}
