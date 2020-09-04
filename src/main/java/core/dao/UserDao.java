package core.dao;

import core.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User create(User user);

    Optional<User> getById(Long userId);

    User update(User user);

    boolean deleteById(Long userId);

    boolean delete(User user);

    List<User> getAllUsers();
}
