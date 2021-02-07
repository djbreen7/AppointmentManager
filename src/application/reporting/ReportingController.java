package application.reporting;

import dao.ContactDao;
import implementations.ContactDaoImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.AppointmentSummaryReport;
import model.Contact;
import model.Customer;
import model.ScheduleReport;
import reporting.Reporter;
import reporting.ReporterImpl;
import utilities.Lambdas;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ReportingController implements Initializable {
    private Reporter reporter;
    private ContactDao contactDao;

    private ObservableList<AppointmentSummaryReport> summaryReport;
    private ObservableList<ScheduleReport> scheduleReport;
    private ObservableList<Customer> customerAuditReport;

    private ObservableList<Contact> contacts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reporter = new ReporterImpl();
        contactDao = new ContactDaoImpl();

        contacts = FXCollections.observableList(contactDao.getAllContacts());
        summaryReport = FXCollections.observableList(reporter.getSummaryReport());
        customerAuditReport = FXCollections.observableList(reporter.getCustomerAuditReport());

        configureReportTypeComboBox();
        configureContactComboBox();

        configureSummaryReportTable();
        configureCustomerAuditReportTable();
        configureContactScheduleReportTable();
    }

    /**
     * Adds report type options to Report Type Combo Box.
     */
    private void configureReportTypeComboBox() {
        var reportTypes = FXCollections
                .observableList(Arrays.asList("Appointment Summary", "Contact Schedule", "Customer Audit"));

        reportTypeComboBox.setItems(reportTypes);
        reportTypeComboBox.setValue("Appointment Summary");
    }

    /**
     * Configures combo box mapping to/from and set values for Contact Combo Box.
     */
    private void configureContactComboBox() {
        var contactId = contacts.get(0).getContactId();
        scheduleReport = FXCollections.observableList(reporter.getScheduleReport(contactId));

        contactComboBox.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {
                if (contact != null)
                    return contact.getName();

                return null;
            }

            @Override
            public Contact fromString(final String string) {
                // Justification: Clean combo box data mapping / Reusable code
                return Lambdas.getContactByName(contactComboBox.getItems(), string);
            }
        });

        contactComboBox.setItems(contacts);
        contactComboBox.setValue(contacts.get(0));
        contactLabel.setVisible(false);
        contactComboBox.setVisible(false);
    }

    /**
     * Seeds the Appointment Summary Table.
     */
    private void configureSummaryReportTable() {
        summaryReportTable.setItems(summaryReport);

        apptMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        summaryReportTable.setVisible(true);
    }

    /**
     * Seeds the Customer Audit Report Table.
     */
    private void configureCustomerAuditReportTable() {
        customerAuditTable.setItems(customerAuditReport);

        auditCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        auditCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        auditLastUpdate.setCellValueFactory(cell -> new SimpleStringProperty(getFriendlyDate(cell.getValue().getLastUpdate())));
        auditLastUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        auditCreateDate.setCellValueFactory(cell -> new SimpleStringProperty(getFriendlyDate(cell.getValue().getCreateDate())));
        auditCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
    }

    /**
     * Seeds the Contact Schedule Report Table
     */
    private void configureContactScheduleReportTable() {
        scheduleTable.setItems(scheduleReport);

        scheduleContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        scheduleAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        scheduleCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        scheduleStartCol.setCellValueFactory(cell -> new SimpleStringProperty(getFriendlyDate(cell.getValue().getStart())));
        scheduleEndCol.setCellValueFactory(cell -> new SimpleStringProperty(getFriendlyDate(cell.getValue().getEnd())));
        scheduleTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        scheduleTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        scheduleDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        summaryReportTable.setVisible(true);
    }

    /**
     * Returns a human readable date.
     *
     * @param cal The date to format.
     * @return A date in "yyyy-MM-dd hh:mm aa" format.
     */
    private String getFriendlyDate(Calendar cal) {
        var formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        return formatter.format(cal.getTime());
    }

    @FXML
    private TableView<AppointmentSummaryReport> summaryReportTable;

    @FXML
    private TableColumn<AppointmentSummaryReport, String> apptMonthCol;

    @FXML
    private TableColumn<AppointmentSummaryReport, String> apptTypeCol;

    @FXML
    private TableColumn<AppointmentSummaryReport, Integer> apptTotalCol;

    @FXML
    private TableView<ScheduleReport> scheduleTable;

    @FXML
    private TableColumn<ScheduleReport, Integer> scheduleContactIdCol;

    @FXML
    private TableColumn<ScheduleReport, Integer> scheduleAppointmentIdCol;

    @FXML
    private TableColumn<ScheduleReport, Integer> scheduleCustomerIdCol;

    @FXML
    private TableColumn<ScheduleReport, String> scheduleStartCol;

    @FXML
    private TableColumn<ScheduleReport, String> scheduleEndCol;

    @FXML
    private TableColumn<ScheduleReport, String> scheduleTitleCol;

    @FXML
    private TableColumn<ScheduleReport, String> scheduleTypeCol;

    @FXML
    private TableColumn<ScheduleReport, String> scheduleDescriptionCol;

    @FXML
    private TableView<Customer> customerAuditTable;

    @FXML
    private TableColumn<Customer, Integer> auditCustomerId;

    @FXML
    private TableColumn<Customer, String> auditCustomerName;

    @FXML
    private TableColumn<Customer, String> auditLastUpdate;

    @FXML
    private TableColumn<Customer, String> auditLastUpdatedBy;

    @FXML
    private TableColumn<Customer, String> auditCreateDate;

    @FXML
    private TableColumn<Customer, String> auditCreatedBy;

    @FXML
    private ComboBox<String> reportTypeComboBox;

    @FXML
    private Label contactLabel;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    void handleReportTypeComboBoxAction(ActionEvent event) {
        summaryReportTable.setVisible(false);
        scheduleTable.setVisible(false);
        customerAuditTable.setVisible(false);
        contactLabel.setVisible(false);
        contactComboBox.setVisible(false);

        switch (reportTypeComboBox.getValue()) {
            case "Contact Schedule":
                scheduleTable.setVisible(true);
                contactLabel.setVisible(true);
                contactComboBox.setVisible(true);
                break;
            case "Customer Audit":
                customerAuditTable.setVisible(true);
                break;
            case "Appointment Summary":
            default:
                summaryReportTable.setVisible(true);
                break;
        }
    }

    @FXML
    void handleContactComboBoxAction(ActionEvent event) {
        var contactId = contactComboBox.getValue().getContactId();

        scheduleReport = FXCollections.observableList(reporter.getScheduleReport(contactId));
        scheduleTable.setItems(scheduleReport);
        scheduleTable.refresh();
    }
}
