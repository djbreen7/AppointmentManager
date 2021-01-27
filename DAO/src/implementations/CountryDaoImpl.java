package implementations;

import data.DatabaseConnection;
import interfaces.CountryDao;
import model.Country;
import utilities.ResultSetBuilder;

import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl implements CountryDao {
    private ResultSetBuilder resultSetBuilder;

    public CountryDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

    @Override
    public List<Country> getAllCountries() {
        String query = String.format("SELECT * FROM countries");
        List<Country> countries = new ArrayList();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                var country = resultSetBuilder.buildCountryResult(resultSet, false);
                countries.add(country);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
            return countries;
        }
    }

    @Override
    public Country getCountry(int countryId) {
        return null;
    }

    @Override
    public void updateCountry(Country country) {

    }

    @Override
    public void deleteCountry(int countryId) {

    }
}
