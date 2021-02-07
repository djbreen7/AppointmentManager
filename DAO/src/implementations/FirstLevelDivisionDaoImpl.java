package implementations;

import data.DatabaseConnection;
import dao.FirstLevelDivisionDao;
import model.FirstLevelDivision;
import utilities.ResultSetBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for working with Divisions in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class FirstLevelDivisionDaoImpl implements FirstLevelDivisionDao {
    private final ResultSetBuilder resultSetBuilder;

    public FirstLevelDivisionDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

    /**
     * Get all divisions from the database.
     *
     * @return All divisions in the database.
     */
    @Override
    public List<FirstLevelDivision> getAllDivisions() {
        String query = "SELECT * FROM first_level_divisions";
        List<FirstLevelDivision> divisions = new ArrayList<>();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                var division = resultSetBuilder.buildDivisionResult(resultSet, false);
                divisions.add(division);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return divisions;
        }
    }
}
