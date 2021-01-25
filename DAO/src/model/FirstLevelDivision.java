package model;

import java.util.Calendar;

public class FirstLevelDivision extends BaseEntity {
    int divisionId;
    int countryId;
    String division;

    public FirstLevelDivision(int divisionId, int countryId, String division, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdatedBy) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
        this.divisionId = divisionId;
        this.countryId = countryId;
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

    public void setDivision(String division) {
        this.division = division;
    }
}
