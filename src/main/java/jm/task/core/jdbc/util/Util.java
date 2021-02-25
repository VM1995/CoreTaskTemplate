package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;
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

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try (InputStream in = Util.class.getClassLoader().getResourceAsStream("db.properties")) {
                Properties config = new Properties();
                config.load(in);
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                Map<String, Object> settings = new HashMap<>();
                settings.put(Environment.DRIVER, config.getProperty("dataSource"));
                settings.put(Environment.URL, config.getProperty("url"));
                settings.put(Environment.USER, config.getProperty("username"));
                settings.put(Environment.PASS, config.getProperty("password"));
                registryBuilder.applySettings(settings);
                registry = registryBuilder.build();
                MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(User.class);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}
