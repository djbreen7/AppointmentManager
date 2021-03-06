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
import javafx.fxml.Initializable;
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

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class CustomersUpsertController implements Initializable {
    private CustomerDao customerDao;
    private CountryDao countryDao;
    private FirstLevelDivisionDao divisionDao;
    private SceneManager sceneManager;
    private DataManager dataManager;
    private UserManager userManager;
    private Customer customer;
    private ObservableList<Country> countries;
    private ObservableList<FirstLevelDivision> divisions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        configureComboBoxes();
        setComboBoxValues();
    }

    /**
     * Creates or retrieves an appointment to use in the form.
     *
     * @return A Customer object.
     */
    private Customer initializeCustomer() {
        var customerId = dataManager.getAndClearCustomerId();

        if (customerId == 0)
            return new Customer();

        return customerDao.getCustomer(customerId);
    }

    /**
     * Configures combo box mapping to/from.
     */
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
                // Justification: Clean combo box data mapping / Reusable code
                return Lambdas.getDivisionByName(divisionComboBox.getItems(), string);
            }
        });

        countryComboBox.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country.getCountry();
            }

            @Override
            public Country fromString(final String string) {
                // Justification: Clean combo box data mapping / Reusable code
                return Lambdas.getCountryByName(countryComboBox.getItems(), string);
            }
        });
    }

    /**
     * Seeds combo boxes.
     */
    private void setComboBoxValues() {
        var isNewCustomer = customer.getCustomerId() == 0;

        // Justification: Keeps the ternary easy to read
        var activeCountry = isNewCustomer
                ? Lambdas.getCountryByName(countries, "U.S")
                : customer.getDivision().getCountry();
        var activeDivision = isNewCustomer
                ? null
                : customer.getDivision();
        // Justification: Reusable code
        var activeDivisions = FXCollections.observableList(Lambdas.getDivisionsByCountryId(divisions, activeCountry.getCountryId()));

        countryComboBox.setItems(countries);
        divisionComboBox.setItems(activeDivisions);
        countryComboBox.setValue(activeCountry);
        divisionComboBox.setValue(activeDivision);
    }

    /**
     * Seeds the Customer Form.
     */
    private void setupForm() {
        customerIdTextField.setText(Integer.toString(customer.getCustomerId()));

        nameTextField.setText(customer.getName());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhone());

        if (customer.getCustomerId() == 0)
            customerIdTextField.setText("N/A");
    }

    private void resetErrors() {
        divisionComboBox.getStyleClass().remove("error");
        countryComboBox.getStyleClass().remove("error");
        nameTextField.getStyleClass().remove("error");
        errorLabel.setVisible(false);
    }

    private boolean formIsValid() {
        var isValid = true;
        var requiredItems = Arrays.asList(divisionComboBox, countryComboBox);
        for (var i = 0; i < requiredItems.stream().count(); i++) {
            var current = requiredItems.get(i);
            if (current.getValue() == null) {
                current.getStyleClass().add("error");
                isValid = false;
            }
        }

        if (nameTextField.getText().isEmpty()) {
            nameTextField.getStyleClass().add("error");
            isValid = false;
        }

        return isValid;
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
    private Label errorLabel;

    @FXML
    private void handleCountryComboAction(ActionEvent event) {
        var newCountry = countryComboBox.getValue();
        // Justification: Reusable code
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
        resetErrors();
        if (!formIsValid()) {
            errorLabel.setText("The highlighted items are required");
            errorLabel.setVisible(true);
            return;
        }

        var customerId = customerIdTextField.getText();
        customer.setCustomerId(customerId.equals("N/A") ? 0 : Integer.parseInt(customerId));
        customer.setName(nameTextField.getText());
        customer.setPhone(phoneTextField.getText());
        customer.setAddress(addressTextField.getText());
        customer.setPostalCode(postalCodeTextField.getText());
        customer.setDivisionId(divisionComboBox.getValue().getDivisionId());
        customer.setLastUpdatedBy(userManager.getCurrentUser().getUserName());

        if (customer.getCustomerId() == 0)
            customer.setCreatedBy(userManager.getCurrentUser().getUserName());

        customerDao.upsertCustomer(customer);

        sceneManager.goToScene(sceneManager.CUSTOMERS_SCENE);
    }
}
