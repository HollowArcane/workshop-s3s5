package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import toolkit.util.Debug;

public class DB
{
    private static Properties config = null;
    private static String url = null;
    private static String user = null;
    private static String password = null;
    private static String driver = null;

    private DB() {}

    private static void loadProperties()
        throws ClassNotFoundException
    {
        config = new Properties();
        try (InputStream input = DB.class.getClassLoader().getResourceAsStream("config.db.properties"))
        {
            if (input == null) 
            {
                Debug.warning("File not found: ", "'config.db.properties'");
                return;
            }
            config.load(input);

            String database = config.getProperty("database").trim();
            url = config.getProperty("url") + (database == null || database.trim().isEmpty() ? "": "/" + database.trim());
            user = config.getProperty("user").trim();
            password = config.getProperty("password");
            driver = config.getProperty("driver").trim();

            Debug.info("URL: ", url);
            Debug.info("USER: ", user);
            Debug.info("PASSWORD: ", password);
            Debug.info("DRIVER: ", driver);
         
            // Load the driver class
            Class.forName(driver);
        }
        catch (IOException e)
        { e.printStackTrace(); }
    }

    public static <T> T execute(Function<Connection, T> consumer)
        throws SQLException,
               ClassNotFoundException
    {
        Objects.requireNonNull(consumer);
        if(config == null)
        { loadProperties(); }

        T result = null;
        try(Connection connection = DriverManager.getConnection(url, user, password))
        {
            connection.setAutoCommit(false);
            Debug.success("Connection established successfully!");
    
            result = consumer.apply(connection);
            connection.commit();
        }
        Debug.success("Connection closed successfully!");
        return result;
    }

    public static <T> T handle(Function<DSLContext, T> consumer)
        throws SQLException,
               ClassNotFoundException
    {
        Objects.requireNonNull(consumer);
        if(config == null)
        { loadProperties(); }

        T result = null;
        try(Connection connection = DriverManager.getConnection(url, user, password))
        {
            connection.setAutoCommit(false);
            Debug.success("Connection established successfully!");
            
            result = consumer.apply(DSL.using(connection, SQLDialect.POSTGRES));
            connection.commit();
        }
        Debug.success("Connection closed successfully!");
        return result;
    }
}
