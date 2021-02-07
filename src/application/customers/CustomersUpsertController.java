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

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
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
        configureComboBoxes();
        setComboBoxValues();
    }

    
    /** 
     * @return Customer
     */
    private Customer initializeCustomer() {
        var customerId = dataManager.getAndClearCustomerId();

        if (customerId == 0)
            return new Customer();

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

    private void setupForm() {
        customerIdTextField.setText(Integer.toString(customer.getCustomerId()));

        nameTextField.setText(customer.getName());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhone());

        if (customer.getCustomerId() == 0)
            customerIdTextField.setText("N/A");
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

    
    /** 
     * @param event
     */
    @FXML
    private void handleCountryComboAction(ActionEvent event) {
        var newCountry = countryComboBox.getValue();
        // Justification: Reusable code
        var result = FXCollections.observableList(Lambdas.getDivisionsByCountryId(divisions, newCountry.getCountryId()));

        divisionComboBox.setItems(result);
        divisionComboBox.setValue(null);
    }

    
    /** 
     * @param event
     */
    @FXML
    private void handleCancelBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.CUSTOMERS_SCENE);
    }

    
    /** 
     * @param event
     */
    @FXML
    private void handleSaveBtnAction(ActionEvent event) {
        customer.setCustomerId(Integer.parseInt(customerIdTextField.getText()));
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
