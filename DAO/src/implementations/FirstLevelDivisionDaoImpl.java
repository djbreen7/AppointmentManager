package implementations;

import data.DatabaseConnection;
import interfaces.FirstLevelDivisionDao;
import model.FirstLevelDivision;
import utilities.ResultSetBuilder;

import java.util.ArrayList;
import java.util.List;

public class FirstLevelDivisionDaoImpl implements FirstLevelDivisionDao {
    private ResultSetBuilder resultSetBuilder;

    public FirstLevelDivisionDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

    @Override
    public List<FirstLevelDivision> getAllDivisions() {
        String query = String.format(
                "SELECT * FROM first_level_divisions");
        List<FirstLevelDivision> divisions = new ArrayList();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                var division = resultSetBuilder.buildDivisionResult(resultSet, false);
                divisions.add(division);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
            return divisions;
        }
    }

    @Override
    public FirstLevelDivision getDivision(int divisionId) {
        return null;
    }

    @Override
    public void updateDivision(FirstLevelDivision division) {

    }

    @Override
    public void deleteDivision(int divisionId) {

    }
}
