package com.internet.shop.security;

import com.internet.shop.exceptions.AuthenticationException;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import com.internet.shop.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = userService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException("Wrong login or password"));
        String hashedPass = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPass)) {
            return user;
        }
        throw new AuthenticationException("Wrong login or password");
    }
}
