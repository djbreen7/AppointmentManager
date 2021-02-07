package application.customers;

import implementations.CustomerDaoImpl;
import dao.CustomerDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import managers.SceneManager;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class CustomersController implements Initializable {
    private CustomerDao customerDao;
    private SceneManager sceneManager;
    private ObservableList<Customer> customers;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerDao = new CustomerDaoImpl();
        sceneManager = SceneManager.getInstance();
        customers = FXCollections.observableList(customerDao.getAllCustomers());

        configureCustomersTable();
    }

    /**
     * Adds functionality to the Customers Table and seeds the values.
     */
    private void configureCustomersTable() {
        customersTable.setItems(customers);

        customersTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                var index = customersTable.getSelectionModel().getFocusedIndex();
                var customerId = customersTable.getItems().get(index).getCustomerId();

                sceneManager.goToScene(sceneManager.CUSTOMER_UPSERT_SCENE, customerId);
            }
        });

        modifyBtn.disableProperty().bind(customersTable.getSelectionModel().selectedItemProperty().isNull());
        deleteBtn.disableProperty().bind(customersTable.getSelectionModel().selectedItemProperty().isNull());
        scheduleAppointmentBtn.disableProperty().bind(customersTable.getSelectionModel().selectedItemProperty().isNull());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        divisionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivision().getDivision()));
        countryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivision().getCountry().getCountry()));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customersTable.getSortOrder().add(customerIdCol);
        customersTable.sort();
    }

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    private TableColumn<Customer, String> divisionCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button modifyBtn;

    @FXML
    private Button scheduleAppointmentBtn;

    @FXML
    private void handleDeleteBtnAction(ActionEvent event) {
        var index = customersTable.getSelectionModel().getFocusedIndex();
        var customer = customersTable.getItems().get(index);

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                MessageFormat.format(
                        "Are you sure you want to delete the following customer?\n\nID:\t\t{0}\nName:\t{1}",
                        customer.getCustomerId(), customer.getName()
                )
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) return;

        customerDao.deleteCustomer(customer.getCustomerId());
        initialize(null, null);
    }

    @FXML
    private void handleModifyBtnAction(ActionEvent event) throws IOException {
        var index = customersTable.getSelectionModel().getFocusedIndex();
        var customerId = customersTable.getItems().get(index).getCustomerId();

        sceneManager.goToScene(sceneManager.CUSTOMER_UPSERT_SCENE, customerId);
    }

    @FXML
    private void handleScheduleAppointmentBtnAction(ActionEvent event) {
        var index = customersTable.getSelectionModel().getFocusedIndex();
        var customerId = customersTable.getItems().get(index).getCustomerId();

        sceneManager.goToScene(sceneManager.APPOINTMENT_SCHEDULE_SCENE, customerId);
    }

    @FXML
    private void handleAddCustomerBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.CUSTOMER_UPSERT_SCENE);
    }
}
