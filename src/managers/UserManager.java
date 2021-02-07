package managers;

import model.User;

/**
 * A Singleton class for managing the current user.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class UserManager {
    private static UserManager instance = null;
    private User currentUser;

    private UserManager() {

    }

    /**
     * Creates a UserManager instance if once doesn't exist, then returns the instance.
     *
     * @return UserManager
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    
    /**
     * Gets the current user.
     *
     * @return User
     */
    public User getCurrentUser() {
        return currentUser;
    }

    
    /**
     * Sets the current user to the user that has logged in.
     *
     * @param currentUser
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
