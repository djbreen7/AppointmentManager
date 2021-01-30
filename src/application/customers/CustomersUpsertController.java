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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import managers.DataManager;
import managers.SceneManager;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import utilities.Lambdas;

public class CustomersUpsertController {
    private CustomerDao customerDao;
    private CountryDao countryDao;
    private FirstLevelDivisionDao divisionDao;
    private SceneManager sceneManager;
    private DataManager dataManager;
    private Customer customer;
    private ObservableList<Country> countries;
    private ObservableList<FirstLevelDivision> divisions;

    public void initialize() {
        customerDao = new CustomerDaoImpl();
        countryDao = new CountryDaoImpl();
        divisionDao = new FirstLevelDivisionDaoImpl();
        sceneManager = SceneManager.getInstance();
        dataManager = DataManager.getInstance();
        customer = initializeCustomer();
        countries = FXCollections.observableList(countryDao.getAllCountries());
        divisions = FXCollections.observableList(divisionDao.getAllDivisions());

        setupForm();
    }

    private Customer initializeCustomer() {
        var customerId = dataManager.getAndClearDataId();

        if (customerId == -1) {
            return new Customer(-1, -1, null, null, null, null, null, null, null, null, null);
        }

        return customerDao.getCustomer(customerId);
    }

    private void configureComboBoxes() {
        divisionComboBox.setConverter(new StringConverter<FirstLevelDivision>() {
            @Override
            public String toString(FirstLevelDivision division) {
                return division.getDivision();
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
        Country activeCountry = isNewCustomer
                ? Lambdas.getCountryByName(countries, "U.S")
                : customer.getDivision().getCountry();
        FirstLevelDivision activeDivision = isNewCustomer
                ? Lambdas.getDivisionByName(divisions, "Alabama")
                : customer.getDivision();
        ObservableList<FirstLevelDivision> activeDivisions = FXCollections.observableList(Lambdas.getDivisionsByCountryId(divisions ,activeCountry.getCountryId()));

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
    private Button saveBtn;

    @FXML
    private Button deleteDtn;

    @FXML
    private Label customerIdLabel;

    @FXML
    private TextField customerIdTextField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label addressLabel;

    @FXML
    private TextField addressTextField;

    @FXML
    private Label postalCodeLabel;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private ComboBox<FirstLevelDivision> divisionComboBox;

    @FXML
    private Label divisionLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Label countryLabel;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    void handleCancelBtnAction(ActionEvent event) {
        sceneManager.goToScene(SceneManager.CUSTOMERS_SCENE);
    }

    @FXML
    void handleSaveBtnAction(ActionEvent event) {
        System.out.println("handleSaveBtnAction");
    }
}
