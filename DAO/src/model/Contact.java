package model;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class Contact {
    private int contactId;
    private String name;
    private String email;

    
    /** 
     * @return int
     */
    public int getContactId() {
        return contactId;
    }

    
    /** 
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    
    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
