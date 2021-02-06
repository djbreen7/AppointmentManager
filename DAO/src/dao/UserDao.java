package dao;

import model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User getUser(String userName);
}
