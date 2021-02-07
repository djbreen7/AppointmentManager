package model;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class Country extends BaseEntity implements NamedItem {
    private int countryId;
    private String country;

    public int getCountryId() {
        return countryId;
    }
    
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String getName() {
        return getCountry();
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
