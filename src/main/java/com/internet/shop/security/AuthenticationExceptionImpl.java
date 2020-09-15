package com.internet.shop.security;

import com.internet.shop.exceptions.AuthenticationException;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;

@Service
public class AuthenticationExceptionImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        final String exceptionMessage = "Wrong login or password";

        User user = userService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException(exceptionMessage));
        if (user.getPassword().equals(password)) {
            return user;
        }
        throw new AuthenticationException(exceptionMessage);
    }
}
