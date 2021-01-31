package application.customers;

import dao.CountryDao;
import dao.CustomerDao;
import dao.FirstLevelDivisionDao;
import implementations.CountryDaoImpl;
import implementations.CustomerDaoImpl;
import implementations.FirstLevelDivisionDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import managers.DataManager;
import managers.SceneManager;
import managers.UserManager;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import utilities.Lambdas;

import java.util.Calendar;

public class CustomersUpsertController {
    private CustomerDao customerDao;
    private CountryDao countryDao;
    private FirstLevelDivisionDao divisionDao;
    private SceneManager sceneManager;
    private DataManager dataManager;
    private UserManager userManager;
    private Customer customer;
    private ObservableList<Country> countries;
    private ObservableList<FirstLevelDivision> divisions;

    public void initialize() {
        customerDao = new CustomerDaoImpl();
        countryDao = new CountryDaoImpl();
        divisionDao = new FirstLevelDivisionDaoImpl();
        sceneManager = SceneManager.getInstance();
        dataManager = DataManager.getInstance();
        userManager = UserManager.getInstance();
        customer = initializeCustomer();
        countries = FXCollections.observableList(countryDao.getAllCountries());
        divisions = FXCollections.observableList(divisionDao.getAllDivisions());

        setupForm();
        temp();
    }

    private void temp() {
        var startDate = customer.getLastUpdate();
        System.out.println(startDate.get(Calendar.HOUR_OF_DAY));
    }

    private Customer initializeCustomer() {
        var customerId = dataManager.getAndClearCustomerId();

        if (customerId == -1) {
            return new Customer(-1, -1, null, null, null, null, null, null, null, null, null);
        }

        return customerDao.getCustomer(customerId);
    }

    private void configureComboBoxes() {
        divisionComboBox.setConverter(new StringConverter<FirstLevelDivision>() {
            @Override
            public String toString(FirstLevelDivision division) {
                if (division != null)
                    return division.getDivision();

                return null;
            }
            @Override
            public FirstLevelDivision fromString(final String string) {
                return divisionComboBox.getItems().stream().filter(x -> x.getDivision().equals(string)).findFirst().orElse(null);
            }
        });

        countryComboBox.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country.getCountry();
            }
            @Override
            public Country fromString(final String string) {
                return countryComboBox.getItems().stream().filter(x -> x.getCountry().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void setComboBoxValues() {
        var isNewCustomer = customer.getCustomerId() == -1;
        var activeCountry = isNewCustomer
                ? Lambdas.getCountryByName(countries, "U.S")
                : customer.getDivision().getCountry();
        var activeDivision = isNewCustomer
                ? null
                : customer.getDivision();
        var activeDivisions = FXCollections.observableList(Lambdas.getDivisionsByCountryId(divisions, activeCountry.getCountryId()));

        countryComboBox.setItems(countries);
        divisionComboBox.setItems(activeDivisions);
        countryComboBox.setValue(activeCountry);
        divisionComboBox.setValue(activeDivision);
    }

    private void setupForm() {
        customerIdTextField.setText(Integer.toString(customer.getCustomerId()));

        nameTextField.setText(customer.getName());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhone());

        if (customer.getCustomerId() == -1) {
            customerIdLabel.setVisible(false);
            customerIdTextField.setVisible(false);
        }

        configureComboBoxes();
        setComboBoxValues();
    }

    @FXML
    private Label customerIdLabel;

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private ComboBox<FirstLevelDivision> divisionComboBox;

    @FXML
    private TextField phoneTextField;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private void handleScheduleAppointmentBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.APPOINTMENT_SCHEDULE_SCENE, customer.getCustomerId());
    }

    @FXML
    private void handleCountryComboAction(ActionEvent event) {
        var newCountry = countryComboBox.getValue();
        var result = FXCollections.observableList(Lambdas.getDivisionsByCountryId(divisions, newCountry.getCountryId()));

        divisionComboBox.setItems(result);
        divisionComboBox.setValue(null);
    }

    @FXML
    private void handleCancelBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.CUSTOMERS_SCENE);
    }

    @FXML
    private void handleSaveBtnAction(ActionEvent event) {
        customer.setCustomerId(Integer.parseInt(customerIdTextField.getText()));
        customer.setName(nameTextField.getText());
        customer.setPhone(phoneTextField.getText());
        customer.setAddress(addressTextField.getText());
        customer.setPostalCode(postalCodeTextField.getText());
        customer.setDivisionId(divisionComboBox.getValue().getDivisionId());
        customer.setLastUpdatedBy(userManager.getCurrentUser().getUserName());

        if (customer.getCustomerId() == -1)
            customer.setCreatedBy(userManager.getCurrentUser().getUserName());

        customerDao.upsertCustomer(customer);

        sceneManager.goToScene(sceneManager.CUSTOMERS_SCENE);
    }
}
