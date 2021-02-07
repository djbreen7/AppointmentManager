package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A connection manager for the MySQL database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class DatabaseConnection {
    private static final String databaseName = "WJ07dC8";
    private static final String DB_URL = "jdbc:mysql://wgudb.ucertify.com/" + databaseName;
    private static final String username = "U07dC8";
    private static final String password = "53688996744";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    public static Connection connection;

    /**
     * Opens a connection to the database.
     */
    public static void makeConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(DB_URL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Closes the connection to the database.
     */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
