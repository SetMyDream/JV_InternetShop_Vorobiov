package core.service;

import core.dao.UserDao;
import core.lib.Inject;
import core.lib.Service;
import core.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User getById(Long userId) {
        return userDao.getById(userId).get();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(User user) {
        return userDao.delete(user);
    }

    @Override
    public boolean deleteById(Long userId) {
        return userDao.deleteById(userId);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAllUsers();
    }
}
