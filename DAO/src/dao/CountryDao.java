package dao;

import model.Country;

import java.util.List;

/**
 * DAO for working with Countries in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface CountryDao {

    /**
     * Get all countries from the database.
     *
     * @return List of Country
     */
    List<Country> getAllCountries();
}
