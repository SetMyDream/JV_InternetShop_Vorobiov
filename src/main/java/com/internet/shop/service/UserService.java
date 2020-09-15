package com.internet.shop.service;

import com.internet.shop.model.User;
import java.util.List;

public interface UserService extends GenericService<User, Long> {
    List<User> getAll();

    User update(User user);
}
