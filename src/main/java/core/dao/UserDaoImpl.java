package core.dao;

import core.db.Storage;
import core.model.Order;
import core.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> getById(Long userId) {
        return Storage.users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();
    }

    @Override
    public User update(User user) {
        IntStream.range(0, Storage.users.size())
                .filter(i -> Storage.users.get(i).getId().equals(user.getId()))
                .forEach(i -> Storage.users.set(i, user));
        return user;
    }

    @Override
    public boolean deleteById(Long userId) {
        return Storage.users.removeIf(user -> user.getId().equals(userId));
    }

    @Override
    public boolean delete(User user) {
        return Storage.users.remove(user);
    }

    @Override
    public List<User> getAllUsers() {
        return Storage.users;
    }
}
