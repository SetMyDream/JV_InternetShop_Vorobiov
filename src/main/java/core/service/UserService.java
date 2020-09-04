package core.service;

import core.model.User;

import java.util.List;

public interface UserService {
    User create(User product);

    User getById(Long productId);

    User update(User product);

    boolean delete(User product);

    boolean deleteById(Long productId);

    List<User> getAll();
}
