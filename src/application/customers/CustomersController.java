package application.customers;

import implementations.CustomerDaoImpl;
import dao.CustomerDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import managers.SceneManager;
import model.Customer;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

public class CustomersController {
    private CustomerDao customerDao;
    private SceneManager sceneManager;
    private ObservableList<Customer> customers;

    public void initialize() {
        customerDao = new CustomerDaoImpl();
        sceneManager = SceneManager.getInstance();
        customers = FXCollections.observableList(customerDao.getAllCustomers());

        configureCustomersTable();
        customersTable.setItems(customers);
    }

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer,Integer> customerIdCol;

    @FXML
    private TableColumn<Customer,String> nameCol;

    @FXML
    private TableColumn<Customer,String> addressCol;

    @FXML
    private TableColumn<Customer,String> postalCodeCol;

    @FXML
    private TableColumn<Customer, String> divisionCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Customer,String> phoneCol;

    @FXML
    private Button modifyCustomerBtn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private Button addCustomerBtn;

    @FXML
    void handleDeleteCustomerBtnAction(ActionEvent event) {
        var index = customersTable.getSelectionModel().getFocusedIndex();
        var customer = customersTable.getItems().get(index);

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                MessageFormat.format(
                        "Are you sure you want to delete the following customer?\n\nID:\t\t{0}\nName:\t{1}",
                        customer.getCustomerId(), customer.getName()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) return;

        customerDao.deleteCustomer(customer.getCustomerId());
    }

    @FXML
    public void handleModifyCustomerBtnAction(ActionEvent event) throws IOException {
        var index = customersTable.getSelectionModel().getFocusedIndex();
        var customerId = customersTable.getItems().get(index).getCustomerId();

        sceneManager.goToScene(sceneManager.CUSTOMER_UPSERT_SCENE, customerId);
    }

    public void handleAddCustomerBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.CUSTOMER_UPSERT_SCENE);
    }

    private void configureCustomersTable() {
        customersTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                var index = customersTable.getSelectionModel().getFocusedIndex();
                var customerId = customersTable.getItems().get(index).getCustomerId();

                sceneManager.goToScene(sceneManager.CUSTOMER_UPSERT_SCENE, customerId);
            }
        });

        modifyCustomerBtn.disableProperty().bind(customersTable.getSelectionModel().selectedItemProperty().isNull());
        deleteCustomerBtn.disableProperty().bind(customersTable.getSelectionModel().selectedItemProperty().isNull());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        divisionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivision().getDivision()));
        countryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivision().getCountry().getCountry()));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }
}
