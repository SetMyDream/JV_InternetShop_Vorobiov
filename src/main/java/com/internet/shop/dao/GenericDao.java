package com.internet.shop.dao;

import com.internet.shop.exceptions.DataProcessingException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T order) throws DataProcessingException;

    Optional<T> getById(K id);

    T update(T t);

    boolean delete(K id);

    List<T> getAll();
}
