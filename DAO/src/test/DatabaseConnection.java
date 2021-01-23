package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnection {
    private static final String databaseName="WJ07dC8";
    private static final String DB_URL="jdbc:mysql://wgudb.ucertify.com/"+databaseName;
    private static final String username="U07dC8";
    private static final String password="53688996744";
    private static final String driver="com.mysql.cj.jdbc.Driver";
    static Connection conn;
    public static void makeConnection()throws ClassNotFoundException, SQLException, Exception
    {
        Class.forName(driver);
        conn=(Connection) DriverManager.getConnection(DB_URL,username,password);
        System.out.println("Connection Successful");
    }
    public static void closeConnection()throws ClassNotFoundException, SQLException, Exception{
        conn.close();
        System.out.println("Connection Closed");
    }
}
