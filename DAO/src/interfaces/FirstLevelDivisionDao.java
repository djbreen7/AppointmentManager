package interfaces;

import model.FirstLevelDivision;

import java.util.List;

public interface FirstLevelDivisionDao {
    public List<FirstLevelDivision> getAllDivisions();
    public FirstLevelDivision getDivision(int divisionId);
    public void updateDivision(FirstLevelDivision division);
    public void deleteDivision(int divisionId);
}
