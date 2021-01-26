package application.customers;

import implementations.CustomerDaoImpl;
import interfaces.CustomerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import managers.DataManager;
import managers.SceneManager;
import model.Customer;

public class CustomersUpsertController {
    private CustomerDao customerDao;
    private SceneManager sceneManager;
    private DataManager dataManager;
    private Customer customer;

    public void initialize() {
        customerDao = new CustomerDaoImpl();
        sceneManager = SceneManager.getInstance();
        dataManager = DataManager.getInstance();
        customer = customerDao.getCustomer(dataManager.getAndClearDataId());

        System.out.println(customer.getName());
    }

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void handleCancelBtnAction(ActionEvent event) {
        System.out.println("handleCancelBtnAction");
    }

    @FXML
    void handleSaveBtnAction(ActionEvent event) {
        System.out.println("handleSaveBtnAction");
    }
}


