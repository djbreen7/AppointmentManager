package dao;

import model.User;

/**
 * DAO for working with Users in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface UserDao {

    /**
     * Get a user by User Name from the database.
     *
     * @param userName The User Name to match.
     * @return User
     */
    User getUser(String userName);
}
