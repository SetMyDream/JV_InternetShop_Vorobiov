package com.internet.shop.service;

import com.internet.shop.model.User;
import java.util.List;

public interface UserService extends GenericSerivce<User, Long> {
    User create(User user);

    List<User> getAll();

    User update(User user);
}
