package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String databaseName="WJ07dC8";
    private static final String DB_URL="jdbc:mysql://wgudb.ucertify.com/"+databaseName;
    private static final String username="U07dC8";
    private static final String password="53688996744";
    private static final String driver="com.mysql.cj.jdbc.Driver";

    public static Connection connection;

    public static void makeConnection()
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(DB_URL,username,password);
            System.out.println("Connection Successful");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection Closed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
