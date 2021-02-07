package dao;

import model.User;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface UserDao {
    User getUser(String userName);
}
