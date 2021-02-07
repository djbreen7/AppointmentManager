package implementations;

import dao.UserDao;
import data.DatabaseConnection;
import model.User;
import utilities.ResultSetBuilder;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class UserDaoImpl implements UserDao {
    ResultSetBuilder resultSetBuilder;

    public UserDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

    
    /** 
     * @param userName
     * @return User
     */
    @Override
    public User getUser(String userName) {
        String query = String.format("SELECT * FROM users WHERE User_Name = '%s'", userName);
        User user = null;
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                user = resultSetBuilder.buildUserResult(result, true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return user;
        }
    }
}
