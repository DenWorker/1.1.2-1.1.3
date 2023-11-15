package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private final static String connectionUrl;
    private final static String userName;
    private final static String password;
    private final static String mySQLDriver;

    static {
        Properties properties = loadPropertiesFromFile("db.properties");
        connectionUrl = properties.getProperty("db.url");
        userName = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
        mySQLDriver = "com.mysql.cj.jdbc.Driver";
    }

    public static Connection createConnectionJDBC_API() throws ClassNotFoundException, SQLException {
        Class.forName(mySQLDriver);
        return DriverManager.getConnection(connectionUrl, userName, password);
    }

    public static SessionFactory createSessionFactoryHibernate() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);

        Properties hibernateProperties = new Properties();
        hibernateProperties.put(Environment.DRIVER, mySQLDriver);
        hibernateProperties.put(Environment.URL, connectionUrl);
        hibernateProperties.put(Environment.USER, userName);
        hibernateProperties.put(Environment.PASS, password);
        hibernateProperties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

        hibernateProperties.put(Environment.SHOW_SQL, "true");
        hibernateProperties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        configuration.setProperties(hibernateProperties);

        return configuration.buildSessionFactory();
    }

    public static Properties loadPropertiesFromFile(String pathName) {
        Properties properties = new Properties();
        try (InputStream input = Util.class.getClassLoader().getResourceAsStream(pathName)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
