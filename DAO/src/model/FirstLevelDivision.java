package model;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class FirstLevelDivision extends BaseEntity implements NamedItem {
    private int divisionId;
    private int countryId;
    private Country country;
    private String division;

    
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
    public String getDivision() {
        return division;
    }

    
    /** 
     * @return String
     */
    @Override
    public String getName() {
        return getDivision();
    }

    
    /** 
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    
    /** 
     * @return Country
     */
    public Country getCountry() {
        return country;
    }

    
    /** 
     * @param country
     */
    public void setCountry(Country country) {
        this.country = country;
    }
}
