package jm.task.core.jdbc.util;


import org.hibernate.SessionFactory;

import org.hibernate.boot.registry.StandardServiceRegistry;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    public static Connection getConnection() throws SQLException {
        Properties config = new Properties();
        try (InputStream in = Util.class.getClassLoader().getResourceAsStream("db.properties")) {
            config.load(in);
            Class.forName(config.getProperty("dataSource"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return DriverManager.getConnection(config.getProperty("url"), config.getProperty("username"), config.getProperty("password"));
    }


}
