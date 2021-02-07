package model;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class Country extends BaseEntity implements NamedItem {
    private int countryId;
    private String country;

    
    /** 
     * @return int
     */
    public int getCountryId() {
        return countryId;
    }

    
    /** 
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    
    /** 
     * @return String
     */
    public String getCountry() {
        return country;
    }

    
    /** 
     * @return String
     */
    @Override
    public String getName() {
        return getCountry();
    }

    
    /** 
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
