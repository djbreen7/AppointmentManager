package model;

import java.util.Calendar;

public class Country extends BaseEntity implements NamedItem {
    private int countryId;
    private String country;

    public Country(
            int countryId,
            String country,
            Calendar createDate,
            String createdBy,
            Calendar lastUpdate,
            String lastUpdatedBy
    ) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);

        this.countryId = countryId;
        this.country = country;
    }

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
