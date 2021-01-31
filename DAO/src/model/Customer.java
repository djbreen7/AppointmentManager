package model;

import java.util.Calendar;

public class Customer extends BaseEntity {
    private int customerId;
    private int divisionId;
    private FirstLevelDivision division;
    private String name;
    private String address;
    private String postalCode;
    private String phone;

    public Customer(
        int customerId,
        int divisionId,
        FirstLevelDivision division,
        String name,
        String address,
        String postalCode,
        String phone,
        Calendar createDate,
        String createdBy,
        Calendar lastUpdate,
        String lastUpdatedBy
    ) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);

        this.customerId = customerId;
        this.divisionId = divisionId;
        this.division = division;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public FirstLevelDivision getDivision() {
        return division;
    }

    public void setDivision(FirstLevelDivision division) {
        this.division = division;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
