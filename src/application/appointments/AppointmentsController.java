package application.appointments;

import dao.AppointmentDao;
import implementations.AppointmentDaoImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import managers.DataManager;
import managers.SceneManager;
import managers.UserManager;
import model.Appointment;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    private AppointmentDao appointmentDao;
    private UserManager userManager;
    private SceneManager sceneManager;
    private DataManager dataManager;
    private ObservableList<Appointment> appointments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentDao = new AppointmentDaoImpl();
        userManager = UserManager.getInstance();
        sceneManager = SceneManager.getInstance();
        dataManager = DataManager.getInstance();
        appointments = FXCollections.observableList(appointmentDao.getAllAppointments(userManager.getCurrentUser().getUserId()));

        appointmentsTable.setItems(appointments);
        configureAppointmentsTable();
    }

    private void configureAppointmentsTable() {
        appointmentsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                var index = appointmentsTable.getSelectionModel().getFocusedIndex();
                var appointmentId = appointmentsTable.getItems().get(index).getAppointmentId();
                var customerId = appointmentsTable.getItems().get(index).getCustomerId();

                sceneManager.goToScene(sceneManager.APPOINTMENT_SCHEDULE_SCENE, customerId, appointmentId);
            }
        });

        modifyBtn.disableProperty().bind(appointmentsTable.getSelectionModel().selectedItemProperty().isNull());
        deleteBtn.disableProperty().bind(appointmentsTable.getSelectionModel().selectedItemProperty().isNull());

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        startCol.setCellValueFactory(cell -> new SimpleStringProperty(getFriendlyDate(cell.getValue().getStart())));
        endCol.setCellValueFactory(cell -> new SimpleStringProperty(getFriendlyDate(cell.getValue().getEnd())));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact().getName()));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        appointmentsTable.getSortOrder().add(startCol);
        appointmentsTable.sort();
    }

    private String getFriendlyDate(Calendar cal) {
        var formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        return formatter.format(cal.getTime());
    }

    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> endCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private Button modifyBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button addAppointmentBtn;

    @FXML
    private RadioButton weekRadio;

    @FXML
    private RadioButton monthRadio;

    @FXML
    void handleAddAppointmentBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.APPOINTMENT_SCHEDULE_SCENE);
    }

    @FXML
    void handleDeleteBtnAction(ActionEvent event) {

    }

    @FXML
    void handleModifyBtnAction(ActionEvent event) {
        var index = appointmentsTable.getSelectionModel().getFocusedIndex();
        var customerId = appointmentsTable.getItems().get(index).getCustomerId();
        var appointmentId = appointmentsTable.getItems().get(index).getAppointmentId();

        sceneManager.goToScene(sceneManager.APPOINTMENT_SCHEDULE_SCENE, customerId, appointmentId);
    }

    @FXML
    void handleMonthRadioClick(ActionEvent event) {

    }

    @FXML
    void handleWeekRadioClick(ActionEvent event) {

    }
}
