package implementations;

import data.DatabaseConnection;
import dao.CountryDao;
import model.Country;
import utilities.ResultSetBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for working with Countries in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class CountryDaoImpl implements CountryDao {
    private final ResultSetBuilder resultSetBuilder;

    public CountryDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

    /**
     * Get all countries from the database.
     *
     * @return All countries in the database.
     */
    @Override
    public List<Country> getAllCountries() {
        String query = String.format("SELECT * FROM countries");
        List<Country> countries = new ArrayList<>();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                var country = resultSetBuilder.buildCountryResult(resultSet, false);
                countries.add(country);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return countries;
        }
    }
}
