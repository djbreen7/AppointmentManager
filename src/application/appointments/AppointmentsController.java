package application.appointments;

import dao.AppointmentDao;
import implementations.AppointmentDaoImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import managers.SceneManager;
import managers.UserManager;
import model.Appointment;
import utilities.Lambdas;

import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class AppointmentsController implements Initializable {
    private AppointmentDao appointmentDao;
    private UserManager userManager;
    private SceneManager sceneManager;

    private ObservableList<Appointment> appointments;
    private ObservableList<Appointment> currentMonthAppointments;
    private ObservableList<Appointment> currentWeekAppointments;

    private Calendar activeCal;
    private int activeWeek;
    private int activeMonth;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentDao = new AppointmentDaoImpl();
        userManager = UserManager.getInstance();
        sceneManager = SceneManager.getInstance();
        appointments = FXCollections.observableList(
                appointmentDao.getAppointmentsByUserId(userManager.getCurrentUser().getUserId())
        );

        activeCal = Calendar.getInstance();
        updateAppointmentsView(0);
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
        cancelBtn.disableProperty().bind(appointmentsTable.getSelectionModel().selectedItemProperty().isNull());

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

    private void updateAppointmentsView(int increment) {
        var endCal = Calendar.getInstance();
        var isMonth = monthRadio.isSelected();

        if (isMonth) {
            activeCal.set(Calendar.MONTH, activeCal.get(Calendar.MONTH) + increment);
            activeCal.set(Calendar.DAY_OF_MONTH, 1);
            endCal.setTimeInMillis(activeCal.getTimeInMillis());
            endCal.add(Calendar.MONTH, 1);
            endCal.add(Calendar.DATE, -1);
        } else {
            activeCal.set(Calendar.DAY_OF_WEEK, activeCal.get(Calendar.DAY_OF_WEEK) - activeCal.get(Calendar.DAY_OF_WEEK) + 2);
            activeCal.set(Calendar.WEEK_OF_YEAR, activeCal.get(Calendar.WEEK_OF_YEAR) + increment);
            endCal.setTimeInMillis(activeCal.getTimeInMillis());
            endCal.add(Calendar.DATE, 6);
        }

        var dateFrom = new SimpleDateFormat("MMM dd").format(activeCal.getTime());
        var dateTo = new SimpleDateFormat("MMM dd").format(endCal.getTime());
        dateRangeLabel.setText(MessageFormat.format("{0} - {1}", dateFrom, dateTo));

        activeMonth = activeCal.get(Calendar.MONTH);
        activeWeek = activeCal.get(Calendar.WEEK_OF_YEAR);
        currentMonthAppointments = FXCollections.observableList(
                Lambdas.getCurrentMonthAppointments(appointments, activeMonth)
        );
        currentWeekAppointments = FXCollections.observableList(
                Lambdas.getCurrentWeekAppointments(appointments, activeWeek - 1)
        );
        appointmentsTable.setItems(isMonth ? currentMonthAppointments : currentWeekAppointments);
        appointmentsTable.refresh();
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
    private Button cancelBtn;

    @FXML
    private RadioButton monthRadio;

    @FXML
    private Label dateRangeLabel;

    @FXML
    void handleAddAppointmentBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.APPOINTMENT_SCHEDULE_SCENE);
    }

    @FXML
    void handleCancelBtnAction(ActionEvent event) {
        var index = appointmentsTable.getSelectionModel().getFocusedIndex();
        var appointment = appointmentsTable.getItems().get(index);
        var okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        var goBackButton = new ButtonType("GO BACK", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                MessageFormat.format(
                        "Are you sure you want to cancel the following appointment?" +
                                "\n\nID:\t\t{0}" +
                                "\nTitle:\t\t{1}" +
                                "\nType:\t{2}",
                        appointment.getAppointmentId(), appointment.getTitle(), appointment.getType()
                ),
                okButton,
                goBackButton
        );

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == goBackButton) return;

        var deleteSuccessful = appointmentDao.deleteAppointment(appointment.getAppointmentId());
        if (!deleteSuccessful) return;

        appointments.remove(Lambdas.getAppointmentById(appointments, appointment.getAppointmentId()));
        updateAppointmentsView(0);
    }

    @FXML
    private void handleModifyBtnAction(ActionEvent event) {
        var index = appointmentsTable.getSelectionModel().getFocusedIndex();
        var customerId = appointmentsTable.getItems().get(index).getCustomerId();
        var appointmentId = appointmentsTable.getItems().get(index).getAppointmentId();

        sceneManager.goToScene(sceneManager.APPOINTMENT_SCHEDULE_SCENE, customerId, appointmentId);
    }

    @FXML
    private void handleMonthRadioClick(ActionEvent event) {
        updateAppointmentsView(0);
    }

    @FXML
    private void handleWeekRadioClick(ActionEvent event) {
        updateAppointmentsView(0);
    }

    @FXML
    private void handlePrevBtnAction(ActionEvent event) {
        updateAppointmentsView(-1);
    }

    @FXML
    private void handleNextBtnAction(ActionEvent event) {
        updateAppointmentsView(1);
    }
}
