package utilities;

import javafx.collections.ObservableList;
import model.*;

import java.util.Calendar;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class Lambdas {

    /**
     * @param list The list of countries to filter.
     * @param str  The name of the country to match.
     * @return Country The first result.
     */
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

    /**
     * @param list The list of divisions to filter.
     * @param str  The name of the division to match.
     * @return The first FirstLevelDivision result.
     */
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

    /**
     * @param list The list of divisions to filter.
     * @param id   The id of the country to match.
     * @return A List of FirstLevelDivision matching with the country id
     */
    public static List<FirstLevelDivision> getDivisionsByCountryId(List<FirstLevelDivision> list, int id) {
        BiFunction<List<FirstLevelDivision>, Integer, List<FirstLevelDivision>> func;
        func = ((List<FirstLevelDivision> a, Integer b) -> {
            return a.stream()
                    .filter(x -> x.getCountryId() == b)
                    .collect(Collectors.toList());
        });
        return func.apply(list, id);
    }

    /**
     * @param list The list of contacts to filter.
     * @param name The name of the contact to match.
     * @return The first Contact result.
     */
    public static Contact getContactByName(ObservableList<Contact> list, String name) {
        BiFunction<List<Contact>, String, Contact> func;
        func = (List<Contact> a, String b) -> {
            return a.stream()
                    .filter(x -> x.getName().equals(b))
                    .findFirst()
                    .orElse(null);
        };
        return func.apply(list, name);
    }

    /**
     * @param list The list of customers to filter.
     * @param name The name of the customer to match.
     * @return Customer
     */
    public static Customer getCustomerByName(ObservableList<Customer> list, String name) {
        BiFunction<List<Customer>, String, Customer> func;
        func = (List<Customer> a, String b) -> {
            return a.stream()
                    .filter(x -> x.getName().equals(b))
                    .findFirst()
                    .orElse(null);
        };
        return func.apply(list, name);
    }

    /**
     * @param list The list of appointments to filter.
     * @param week The week Integer to match.
     * @return List of Appointment
     */
    public static List<Appointment> getCurrentWeekAppointments(List<Appointment> list, int week) {
        BiFunction<List<Appointment>, Integer, List<Appointment>> func;
        func = ((List<Appointment> a, Integer b) -> {
            return a.stream()
                    .filter(x -> x.getStart().get(Calendar.WEEK_OF_YEAR) == b)
                    .collect(Collectors.toList());
        });
        return func.apply(list, week);
    }

    /**
     * @param list  The list of appointments to filter.
     * @param month The month Integer to match.
     * @return List of Appointment
     */
    public static List<Appointment> getCurrentMonthAppointments(List<Appointment> list, int month) {
        BiFunction<List<Appointment>, Integer, List<Appointment>> func;
        func = ((List<Appointment> a, Integer b) -> {
            return a.stream()
                    .filter(x -> x.getStart().get(Calendar.MONTH) == b)
                    .collect(Collectors.toList());
        });
        return func.apply(list, month);
    }

    /**
     * @param list The list of appointments to filter.
     * @param id   The id of the appointment to match.
     * @return Appointment
     */
    public static Appointment getAppointmentById(List<Appointment> list, int id) {
        BiFunction<List<Appointment>, Integer, Appointment> func;
        func = ((List<Appointment> a, Integer b) -> {
            return a.stream()
                    .filter(x -> x.getAppointmentId() == b)
                    .collect(Collectors.toList())
                    .get(0);
        });
        return func.apply(list, id);
    }
}
