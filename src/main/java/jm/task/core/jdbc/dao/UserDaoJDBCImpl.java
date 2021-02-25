package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private String createTable = "CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(30), lastname VARCHAR(30), age TINYINT, PRIMARY KEY(id))";
    private String dropTable = "DROP TABLE IF EXISTS users";
    private String cleanTable = "DELETE FROM users";
    Connection connection = null;
    PreparedStatement statement = null;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        try {
            statement = connection.prepareStatement(createTable);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            statement = connection.prepareStatement(dropTable);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            statement = connection.prepareStatement("INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            statement = connection.prepareStatement("DELETE from users WHERE id = ?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                long id = result.getLong(1);
                String name = result.getString(2);
                String lastName = result.getString(3);
                byte age = result.getByte(4);
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            statement = connection.prepareStatement(cleanTable);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
