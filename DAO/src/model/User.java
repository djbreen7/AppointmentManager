package model;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class User extends BaseEntity {
    private int userId;
    private String userName;
    private String password;

    
    /** 
     * @return int
     */
    public int getUserId() {
        return userId;
    }

    
    /** 
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    
    /** 
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    
    /** 
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    /** 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    
    /** 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
