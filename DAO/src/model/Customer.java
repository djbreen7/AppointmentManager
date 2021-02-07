package model;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class Customer extends BaseEntity {
    private int customerId;
    private int divisionId;
    private FirstLevelDivision division;
    private String name;
    private String address;
    private String postalCode;
    private String phone;

    
    /** 
     * @return int
     */
    public int getCustomerId() {
        return customerId;
    }

    
    /** 
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    
    /** 
     * @return int
     */
    public int getDivisionId() {
        return divisionId;
    }

    
    /** 
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    
    /** 
     * @return FirstLevelDivision
     */
    public FirstLevelDivision getDivision() {
        return division;
    }

    
    /** 
     * @param division
     */
    public void setDivision(FirstLevelDivision division) {
        this.division = division;
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
    public String getAddress() {
        return address;
    }

    
    /** 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    
    /** 
     * @return String
     */
    public String getPostalCode() {
        return postalCode;
    }

    
    /** 
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    
    /** 
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    
    /** 
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
