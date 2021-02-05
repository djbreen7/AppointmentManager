package managers;

import model.User;

/**
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

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
