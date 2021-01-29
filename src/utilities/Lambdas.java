package utilities;

import model.Country;
import model.FirstLevelDivision;

import java.util.List;

public class Lambdas {
    public static Country getCountryByName(List<Country> list, String str) {
        GetListItem<Country> func = (List<Country> a, String b) -> {
            return a.stream().filter(x -> x.getName() == b).findFirst().orElse(null);
        };
        return func.getCountryByName(list, str);
    }

    public static FirstLevelDivision getDivisionByName(List<FirstLevelDivision> list, String str) {
        GetListItem<FirstLevelDivision> func = ((List<FirstLevelDivision> a, String b) -> {
            return a.stream().filter(x -> x.getName() == b).findFirst().orElse(null);
        });
        return func.getCountryByName(list, str);
    }
}
