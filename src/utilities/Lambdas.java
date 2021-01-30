package utilities;

import model.Country;
import model.FirstLevelDivision;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Lambdas {
    public static Country getCountryByName(List<Country> list, String str) {
        BiFunction<List<Country>, String, Country> func;
        func = (List<Country> a, String b) -> {
            return a.stream()
                    .filter(x -> x.getName().equals(b))
                    .findFirst()
                    .orElse(null);
        };
        return func.apply(list, str);
    }

    public static FirstLevelDivision getDivisionByName(List<FirstLevelDivision> list, String str) {
        BiFunction<List<FirstLevelDivision>, String, FirstLevelDivision> func;
        func = (List<FirstLevelDivision> a, String b) -> {
            return a.stream()
                    .filter(x -> x.getName().equals(b))
                    .findFirst()
                    .orElse(null);
        };
        return func.apply(list, str);
    }

    public static List<FirstLevelDivision> getDivisionsByCountryId(List<FirstLevelDivision> list, int id) {
        BiFunction<List<FirstLevelDivision>, Integer, List<FirstLevelDivision>> func;
        func = ((List<FirstLevelDivision> a, Integer b) -> {
            return a.stream()
                    .filter(x -> x.getCountryId() == b)
                    .collect(Collectors.toList());
        });
        return func.apply(list, id);
    }
}
