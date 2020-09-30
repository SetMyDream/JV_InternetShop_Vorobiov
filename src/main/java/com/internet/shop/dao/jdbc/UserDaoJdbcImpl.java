package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.UserDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {

    @Override
    public Optional<User> findByLogin(String login) {
        String selectQuery = "SELECT * FROM users WHERE login = ? AND deleted = FALSE";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = conversionToUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find User with login: " + login, e);
        }
        if (user != null) {
            user.setRoles(getUserRoles(user.getUserId()));
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User create(User user) {
        String insertQuery = "INSERT INTO users (username, login, password) VALUES(?,?,?)";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement =
                connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            setValues(statement, user);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create User with id: " + user.getUserId(), e);
        }
        for (Role role : user.getRoles()) {
            Long roleId = getRoleByRoleName(role.getRoleName().toString());
            addUserRoles(user.getUserId(), roleId);
        }
        return user;
    }

    @Override
    public User update(User user) {
        String updateQuery = "UPDATE users SET username = ?, login = ?, password = ? "
                + "WHERE user_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            setValues(statement, user);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create User with id: " + user.getUserId(), e);
        }
        deleteUserRole(user.getUserId());
        for (Role role : user.getRoles()) {
            addUserRoles(user.getUserId(), role.getRoleId());
        }
        return user;
    }

    @Override
    public Optional<User> getById(Long id) {
        String selectQuery = "SELECT * FROM users WHERE user_id = ? AND deleted = FALSE";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = conversionToUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find User with id: " + id, e);
        }
        if (user != null) {
            user.setRoles(getUserRoles(user.getUserId()));
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean delete(Long id) {
        String updateQuery = "UPDATE users SET deleted = TRUE WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete User with id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String selectQuery = "SELECT * FROM users WHERE deleted = FALSE";
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = conversionToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get Users from DB", e);
        }
        for (User user : users) {
            user.setRoles(getUserRoles(user.getUserId()));
        }
        return users;
    }

    private void setValues(PreparedStatement preparedStatement,
                           User user) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
    }

    private User conversionToUser(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("username");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        return new User(userId, name, login, password);
    }

    private void addUserRoles(long userId, long roleId) {
        String insertQuery = "INSERT INTO user_roles (user_id, role_id) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setLong(1, userId);
            statement.setLong(2, roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add user_role", e);
        }
    }

    private Set<Role> getUserRoles(long userId) {
        String selectQuery = "SELECT r.role_id, r.role_name FROM roles r "
                + "INNER JOIN user_roles ur ON r.role_id = ur.role_id WHERE ur.user_id = ?";
        Set<Role> roles = new HashSet<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = Role.of(resultSet.getString("role_name"));
                role.setRoleId(resultSet.getLong("role_id"));
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get userRoles", e);
        }
        return roles;
    }

    private Long getRoleByRoleName(String roleName) {
        String selectQuery = "SELECT role_id FROM roles WHERE role_name = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, roleName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("role_id");
            }
            throw new DataProcessingException("Can't get role by roleName: " + roleName,
                    new RuntimeException());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get role by roleName: " + roleName, e);
        }
    }

    private void deleteUserRole(Long userId) {
        String deleteQuery = "DELETE FROM user_roles WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete userRole by userId: " + userId, e);
        }
    }
}
