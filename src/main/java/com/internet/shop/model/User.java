package com.internet.shop.model;

import java.util.Set;

public class User {
    private Long userId;
    private String name;
    private String login;
    private String password;
    private byte[] salt;
    private Set<Role> roles;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(Long id, String name, String login, String password, byte[] salt) {
        this.userId = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + userId
                + ", name='" + name + '\'' + ", login='" + login + '\''
                + ", password='" + password + '\'' + '}';
    }
}
