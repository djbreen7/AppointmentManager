package model;

import java.util.Calendar;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class FirstLevelDivision extends BaseEntity implements NamedItem {
    private int divisionId;
    private int countryId;
    private Country country;
    private String division;

    public FirstLevelDivision(
            int divisionId,
            int countryId,
            Country country,
            String division,
            Calendar createDate,
            String createdBy,
            Calendar lastUpdate,
            String lastUpdatedBy
    ) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);

        this.divisionId = divisionId;
        this.countryId = countryId;
        this.country = country;
        this.division = division;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getDivision() {
        return division;
    }

    @Override
    public String getName() {
        return getDivision();
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
