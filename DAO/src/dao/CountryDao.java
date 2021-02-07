package dao;

import model.Country;

import java.util.List;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface CountryDao {
    List<Country> getAllCountries();
}
