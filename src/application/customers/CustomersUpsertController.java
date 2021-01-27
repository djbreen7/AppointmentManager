package application.customers;

import implementations.CountryDaoImpl;
import implementations.CustomerDaoImpl;
import implementations.FirstLevelDivisionDaoImpl;
import interfaces.CountryDao;
import interfaces.CustomerDao;
import interfaces.FirstLevelDivisionDao;
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

import java.util.stream.Collectors;

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

        customer = customerDao.getCustomer(dataManager.getAndClearDataId());
        countries = FXCollections.observableList(countryDao.getAllCountries());
        divisions = FXCollections.observableList(divisionDao.getAllDivisions());

        setupForm(customer);
    }

    public void setupForm(Customer customer) {
        customerIdTextField.setText(Integer.toString(customer.getCustomerId()));
        nameTextField.setText(customer.getName());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhone());

        countryComboBox.setItems(countries);
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

        var divisions = this.divisions.stream()
                .filter(x -> x.getCountryId() == customer.getDivision().getCountryId())
                .map(FirstLevelDivision::getDivision)
                .collect(Collectors.toList());
        var list = FXCollections.observableArrayList(divisions);
        divisionComboBox.getItems().addAll(list);
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
    private ComboBox<String> divisionComboBox;

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
        System.out.println("handleCancelBtnAction");
    }

    @FXML
    void handleSaveBtnAction(ActionEvent event) {
        System.out.println("handleSaveBtnAction");
    }
}
