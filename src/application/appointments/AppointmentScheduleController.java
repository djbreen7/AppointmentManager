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
import utilities.CalendarUtils;

import java.net.URL;
import java.time.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AppointmentScheduleController implements Initializable {
    private AppointmentDao appointmentDao;
    private CustomerDao customerDao;
    private ContactDao contactDao;

    private SceneManager sceneManager;
    private DataManager dataManager;
    private UserManager userManager;

    private Appointment appointment;
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
        setDateAndTime();
        configureComboBoxes();
        setComboBoxItems();

        System.out.println(dataManager.getAndClearCustomerId());
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
                    userManager.getCurrentUser().getUserId(),
                    null,
                    -1,
                    null,
                    null,
                    null,
                    null,
                    null,
                    start,
                    end,
                    null,
                    null,
                    null,
                    null
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
        titleTextField.setText(appointment.getTitle());
        typeTextField.setText(appointment.getType());
        descriptionTextArea.setText(appointment.getDescription());

        if (appointment.getAppointmentId() == -1) {
            appointmentIdLabel.setVisible(false);
            appointmentIdTextField.setVisible(false);
        }
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
                return contactComboBox.getItems().stream().filter(x -> x.getName().equals(string)).findFirst().orElse(null);
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
                return customerComboBox.getItems().stream().filter(x -> x.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void setComboBoxItems() {
        var hours = FXCollections.observableList(Arrays.asList("12","1","2","3","4","5","6","7","8","9","10","11"));
        var minutes = FXCollections.observableList(Arrays.asList("00","15","30","45"));

        startHourComboBox.setItems(hours);
        startMinuteComboBox.setItems(minutes);
        endHourComboBox.setItems(hours);
        endMinuteComboBox.setItems(minutes);
    }

    @FXML
    private Label appointmentIdLabel;

    @FXML
    private TextField appointmentIdTextField;

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
    private void handleCancelBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.CUSTOMERS_SCENE);
    }

    @FXML
    private void handleSaveBtnAction(ActionEvent event) {
        var startDate = getCalendar(datePicker, Integer.parseInt(startHourComboBox.getValue()), Integer.parseInt(startMinuteComboBox.getValue()), startPeriodComboBox.getValue());
        var endDate = getCalendar(datePicker, Integer.parseInt(endHourComboBox.getValue()), Integer.parseInt(endMinuteComboBox.getValue()), endPeriodComboBox.getValue());

        appointment.setAppointmentId(Integer.parseInt(appointmentIdTextField.getText()));
        appointment.setTitle(titleTextField.getText());
        appointment.setDescription(descriptionTextArea.getText());
        appointment.setLocation(locationTextField.getText());
        appointment.setType(typeTextField.getText());
        appointment.setStart(startDate);
        appointment.setEnd(endDate);
        appointment.setCustomerId(customerComboBox.getValue().getCustomerId());
        appointment.setUserId(userManager.getCurrentUser().getUserId());
        appointment.setContactId(contactComboBox.getValue().getContactId());
        appointment.setLastUpdatedBy(userManager.getCurrentUser().getUserName());

        if (appointment.getAppointmentId() == -1)
            appointment.setCreatedBy(userManager.getCurrentUser().getUserName());

        // TODO Needs to save in UTC
        appointmentDao.upsertAppointment(appointment);


        sceneManager.goToScene(sceneManager.CUSTOMERS_SCENE);
    }

    private Calendar getCalendar(DatePicker dp, int hour, int minute, String period) {
        var year = dp.getValue().getYear();
        var month = dp.getValue().getMonthValue();
        var day = dp.getValue().getDayOfMonth();

        if (hour != 12 && period.equals("PM")) hour += 12;

        return CalendarUtils.doStuff(year, month, day, hour, minute);
    }
}
