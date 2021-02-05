package implementations;

import utilities.CalendarUtils;
import data.DatabaseConnection;
import dao.UserDao;
import model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class UserDaoImpl implements UserDao {
    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUser(String userName) {
        String query = String.format("SELECT * FROM users WHERE User_Name = '%s'", userName);
        User user = null;
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while(result.next()) {
                var userId = result.getInt("User_ID");
                var password = result.getString("Password");
                var createDate = CalendarUtils.fromLocalDateTime(result.getObject("Create_Date", LocalDateTime.class));
                var createdBy = result.getString("Created_By");
                var lastUpdate = CalendarUtils.fromLocalDateTime(result.getObject("Last_Update", LocalDateTime.class));
                var lastUpdatedBy = result.getString("Last_Updated_By");
                user = new User(userId, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
            return user;
        }
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int userId) {

    }
}
