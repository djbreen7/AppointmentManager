package interfaces;

import model.Country;

import java.util.List;

public interface CountryDao {
    public List<Country> getAllCountries();
    public Country getCountry(int countryId);
    public void updateCountry(Country country);
    public void deleteCountry(int countryId);
}
