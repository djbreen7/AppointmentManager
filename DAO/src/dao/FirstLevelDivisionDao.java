package dao;

import model.FirstLevelDivision;

import java.util.List;

/**
 * DAO for working with Divisions in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface FirstLevelDivisionDao {

    /**
     * Get all divisions from the database.
     *
     * @return All divisions in the database.
     */
    List<FirstLevelDivision> getAllDivisions();
}
