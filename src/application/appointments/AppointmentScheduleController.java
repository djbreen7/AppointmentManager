package application.appointments;

import dao.AppointmentDao;
import dao.ContactDao;
import dao.CustomerDao;
import implementations.AppointmentDaoImpl;
import implementations.ContactDaoImpl;
import implementations.CustomerDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import managers.DataManager;
import managers.SceneManager;
import managers.UserManager;
import model.Appointment;
import model.Contact;
import model.Customer;
import utilities.BusinessHours;
import utilities.CalendarUtils;
import utilities.Lambdas;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class AppointmentScheduleController implements Initializable {
    private AppointmentDao appointmentDao;
    private CustomerDao customerDao;
    private ContactDao contactDao;

    private SceneManager sceneManager;
    private DataManager dataManager;
    private UserManager userManager;

    private Appointment appointment;
    private ObservableList<Appointment> customerAppointments;
    private ObservableList<Contact> contacts;
    private ObservableList<Customer> customers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentDao = new AppointmentDaoImpl();
        customerDao = new CustomerDaoImpl();
        contactDao = new ContactDaoImpl();

        sceneManager = SceneManager.getInstance();
        dataManager = DataManager.getInstance();
        userManager = UserManager.getInstance();

        customers = FXCollections.observableList(customerDao.getAllCustomers());
        contacts = FXCollections.observableList(contactDao.getAllContacts());

        initializeAppointment();
        initializeContacts();
        initializeCustomers();

        setupForm();
        disablePastDates();
        setDateAndTime();
        configureComboBoxes();
        setComboBoxItems();
    }

    private void initializeAppointment() {
        var appointmentId = dataManager.getAndClearAppointmentId();
        var customerId = dataManager.getAndClearCustomerId();

        if (appointmentId == -1) {
            var start = Calendar.getInstance();
            var end = Calendar.getInstance();

            start.set(Calendar.HOUR_OF_DAY, 12);
            start.set(Calendar.MINUTE, 0);

            end.set(Calendar.HOUR_OF_DAY, 13);
            end.set(Calendar.MINUTE, 0);

            appointment = new Appointment(
                    appointmentId,
                    customerId,
                    null,
                    -1,
                    null,
                    -1,
                    null,
                    "",
                    "",
                    "",
                    "",
                    start,
                    end,
                    null,
                    "",
                    null,
                    ""
            );
            return;
        }

        appointment = appointmentDao.getAppointment(appointmentId);
    }

    private void initializeCustomers() {
        var isNewAppointment = appointment.getAppointmentId() == -1;
        var activeCustomer = isNewAppointment
                ? null
                : appointment.getCustomer();

        customerComboBox.setItems(customers);
        customerComboBox.setValue(activeCustomer);
    }

    private void initializeContacts() {
        var isNewAppointment = appointment.getAppointmentId() == -1;
        var activeContact = isNewAppointment
                ? null
                : appointment.getContact();

        contactComboBox.setItems(contacts);
        contactComboBox.setValue(activeContact);
    }

    private void setupForm() {
        appointmentIdTextField.setText(Integer.toString(appointment.getAppointmentId()));
        userIdIdTextField.setText(Integer.toString(appointment.getUserId()));
        titleTextField.setText(appointment.getTitle());
        typeTextField.setText(appointment.getType());
        descriptionTextArea.setText(appointment.getDescription());

        if (appointment.getAppointmentId() == -1)
            appointmentIdTextField.setText("N/A");

        if (appointment.getUserId() == -1) {
            userIdIdTextField.setText(Integer.toString(userManager.getCurrentUser().getUserId()));
        }
    }

    private void disablePastDates() {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                var today = LocalDate.now();

                setDisable(empty || item.isBefore(today));
            }
        });
    }

    private void setDateAndTime() {
        var zid = ZoneId.systemDefault();
        var startDate = LocalDateTime.ofInstant(appointment.getStart().toInstant(), zid);
        var startHour = startDate.getHour();
        var startPeriod = startDate.getHour() >= 12 ? "PM" : "AM";
        var endDate = LocalDateTime.ofInstant(appointment.getEnd().toInstant(), zid);
        var endHour = endDate.getHour();
        var endPeriod = endDate.getHour() >= 12 ? "PM" : "AM";

        if (startHour > 12) startHour -= 12;
        if (endHour > 12) endHour -= 12;

        datePicker.setValue(LocalDate.from(startDate));

        startHourComboBox.setValue(String.valueOf(startHour));
        startMinuteComboBox.setValue(String.valueOf(String.format("%02d", startDate.getMinute())));
        startPeriodComboBox.setValue(startPeriod);

        endHourComboBox.setValue(String.valueOf(endHour));
        endMinuteComboBox.setValue(String.valueOf(String.format("%02d", endDate.getMinute())));
        endPeriodComboBox.setValue(endPeriod);
    }

    private void configureComboBoxes() {
        contactComboBox.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {
                if (contact != null)
                    return contact.getName();

                return null;
            }

            @Override
            public Contact fromString(final String string) {
                return Lambdas.getContactByName(contactComboBox.getItems(), string);
            }
        });

        customerComboBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                if (customer != null)
                    return customer.getName();

                return null;
            }

            @Override
            public Customer fromString(final String string) {
                return Lambdas.getCustomerByName(customerComboBox.getItems(), string);
            }
        });
    }

    private void setComboBoxItems() {
        var hours = FXCollections.observableList(Arrays.asList("12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
        var minutes = FXCollections.observableList(Arrays.asList("00", "15", "30", "45"));

        startHourComboBox.setItems(hours);
        startMinuteComboBox.setItems(minutes);
        endHourComboBox.setItems(hours);
        endMinuteComboBox.setItems(minutes);
    }

    private void resetErrors() {
        errorLabel.setVisible(false);
        timeErrorLabel.setVisible(false);
        startHourComboBox.getStyleClass().remove("error");
        startMinuteComboBox.getStyleClass().remove("error");
        startPeriodComboBox.getStyleClass().remove("error");
        endHourComboBox.getStyleClass().remove("error");
        endMinuteComboBox.getStyleClass().remove("error");
        endPeriodComboBox.getStyleClass().remove("error");
        titleTextField.getStyleClass().remove("error");
        contactComboBox.getStyleClass().remove("error");
        customerComboBox.getStyleClass().remove("error");
    }

    private boolean formIsValid() {
        var isValid = true;
        var requiredItems = Arrays.asList(customerComboBox, contactComboBox);
        for (var i = 0; i < requiredItems.stream().count(); i++) {
            var current = requiredItems.get(i);
            if (current.getValue() == null) {
                current.getStyleClass().add("error");
                isValid = false;
            }
        }

        if (titleTextField.getText().isEmpty()) {
            titleTextField.getStyleClass().add("error");
            isValid = false;
        }

        return isValid;
    }

    private boolean timeIsValid(int startHour, int endHour, int endMinute) {
        var isValid = true;
        var businessHours = new BusinessHours();
        if (startHour != 12 && startPeriodComboBox.getValue().equals("PM")) startHour += 12;
        if (endHour != 12 && endPeriodComboBox.getValue().equals("PM")) endHour += 12;

        if (startHour >= endHour) {
            startHourComboBox.getStyleClass().add("error");
            endHourComboBox.getStyleClass().add("error");
            isValid = false;
        }

        if (startHour < businessHours.getStartHour()) {
            startHourComboBox.getStyleClass().add("error");
            startMinuteComboBox.getStyleClass().add("error");
            startPeriodComboBox.getStyleClass().add("error");
            isValid = false;
        }

        if (endHour > businessHours.getEndHour()) {
            endHourComboBox.getStyleClass().add("error");
            endMinuteComboBox.getStyleClass().add("error");
            endPeriodComboBox.getStyleClass().add("error");
            isValid = false;
        }

        if (endHour == businessHours.getEndHour() && endMinute > 0) {
            endHourComboBox.getStyleClass().add("error");
            endMinuteComboBox.getStyleClass().add("error");
            endPeriodComboBox.getStyleClass().add("error");
            isValid = false;
        }

        return isValid;
    }

    private boolean hasTimeAvailable(int customerId, Calendar startDate, Calendar endDate) {
        customerAppointments = FXCollections.observableList(appointmentDao.getAppointmentsByCustomerId(customerId));

        var conflicts = customerAppointments.stream().filter(x -> {
            return (x.getStart().compareTo(startDate) <= 0 && x.getEnd().compareTo(startDate) >= 0)
                    || (x.getStart().compareTo(endDate) <= 0 && x.getEnd().compareTo(endDate) >= 0);
        }).collect(Collectors.toList());

        return conflicts.stream().count() == 0;
    }

    private Calendar getCalendar(DatePicker dp, int hour, int minute, String period) {
        var year = dp.getValue().getYear();
        var month = dp.getValue().getMonthValue();
        var day = dp.getValue().getDayOfMonth();

        if (hour != 12 && period.equals("PM")) hour += 12;

        return CalendarUtils.fromValues(year, month, day, hour, minute);
    }

    @FXML
    private Label appointmentIdLabel;

    @FXML
    private TextField appointmentIdTextField;

    @FXML
    private TextField userIdIdTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private ComboBox<String> startHourComboBox;

    @FXML
    private ComboBox<String> startMinuteComboBox;

    @FXML
    private ComboBox<String> startPeriodComboBox;

    @FXML
    private ComboBox<String> endHourComboBox;

    @FXML
    private ComboBox<String> endMinuteComboBox;

    @FXML
    private ComboBox<String> endPeriodComboBox;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private Label timeErrorLabel;

    @FXML
    private void handleCancelBtnAction(ActionEvent event) {
        sceneManager.goToPreviousScene();
    }

    @FXML
    private void handleSaveBtnAction(ActionEvent event) {
        resetErrors();
        var startHour = Integer.parseInt(startHourComboBox.getValue());
        var startMinute = Integer.parseInt(startMinuteComboBox.getValue());
        var endHour = Integer.parseInt(endHourComboBox.getValue());
        var endMinute = Integer.parseInt(endMinuteComboBox.getValue());
        var startDate = getCalendar(datePicker, startHour, startMinute, startPeriodComboBox.getValue());
        var endDate = getCalendar(datePicker, endHour, endMinute, endPeriodComboBox.getValue());

        var hasErrors = false;
        if (!timeIsValid(startHour, endHour, endMinute)) {
            errorLabel.setText("Ensure the start time is before the end time.");
            errorLabel.setVisible(true);
            timeErrorLabel.setText("Please select a time between 8 AM and 10 PM EST.");
            timeErrorLabel.setVisible(true);
            hasErrors = true;
        }

        if (!formIsValid()) {
            errorLabel.setText("The highlighted items are required");
            errorLabel.setVisible(true);
            hasErrors = true;
        } else if (!hasTimeAvailable(customerComboBox.getValue().getCustomerId(), startDate, endDate)) {
            timeErrorLabel.setText("Appointment conflict. Please choose another time.");
            timeErrorLabel.setVisible(true);

            hasErrors = true;
        }

        if (hasErrors) return;

        var appointmentId = appointmentIdTextField.getText();
        appointment.setAppointmentId(appointmentId.equals("N/A") ? -1 : Integer.parseInt(appointmentId));
        appointment.setTitle(titleTextField.getText());
        appointment.setDescription(descriptionTextArea.getText());
        appointment.setLocation(locationTextField.getText());
        appointment.setType(typeTextField.getText());
        appointment.setStart(CalendarUtils.toUtc(startDate));
        appointment.setEnd(CalendarUtils.toUtc(endDate));
        appointment.setCustomerId(customerComboBox.getValue().getCustomerId());
        appointment.setUserId(Integer.parseInt(userIdIdTextField.getText()));
        appointment.setContactId(contactComboBox.getValue().getContactId());
        appointment.setLastUpdatedBy(userManager.getCurrentUser().getUserName());

        if (appointment.getAppointmentId() == -1)
            appointment.setCreatedBy(userManager.getCurrentUser().getUserName());

        appointmentDao.upsertAppointment(appointment);

        sceneManager.goToPreviousScene();
    }
}
