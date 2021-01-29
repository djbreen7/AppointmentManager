package dao;

import model.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers();
    public User getUser(String userName);
    public void updateUser(User user);
    public void deleteUser(int userId);
}
