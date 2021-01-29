package implementations;

import utilities.CalendarUtils;
import data.DatabaseConnection;
import dao.UserDao;
import model.User;

import java.util.List;

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
                var createDate = CalendarUtils.toCalendar(result.getDate("Create_Date"));
                var createdBy = result.getString("Created_By");
                var lastUpdate = CalendarUtils.toCalendar(result.getDate("Last_Update"));
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
