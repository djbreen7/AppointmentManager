package implementations;

import dao.UserDao;
import data.DatabaseConnection;
import model.User;
import utilities.ResultSetBuilder;

/**
 * DAO implementation for working with Users in the database.
 *
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
     * Get a user by User Name from the database.
     *
     * @param userName The User Name to match.
     * @return The user with the provided User Name.
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
