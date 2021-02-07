package application.reporting;

import dao.ContactDao;
import implementations.ContactDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.AppointmentSummaryReport;
import model.Contact;
import model.Customer;
import model.ScheduleReport;
import reporting.Reporter;
import reporting.ReporterImpl;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ReportingController implements Initializable {
    private Reporter reporter;
    private ContactDao contactDao;

    private ObservableList<AppointmentSummaryReport> summaryReport;
    private ObservableList<ScheduleReport> scheduleReport;
    private ObservableList<Customer> customerReport;

    private ObservableList<Contact> contacts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reporter = new ReporterImpl();
        contactDao = new ContactDaoImpl();

        contacts = FXCollections.observableList(contactDao.getAllContacts());
        summaryReport = FXCollections.observableList(reporter.getSummaryReport());
        customerReport = FXCollections.observableList(reporter.getCustomerAuditReport());

        configureReportTypeComboBox();
        configureContactComboBox();

        configureAppointmentsReportTable();
        configureCustomerAuditReportTable();
        configureContactScheduleReportTable();
//        summaryReport = FXCollections.observableList(reporter.getScheduleReport());
    }

    private void configureReportTypeComboBox() {
        var reportTypes = FXCollections
                .observableList(Arrays.asList("Appointment Summary", "Contact Schedule", "Customer Audit"));

        reportTypeComboBox.setItems(reportTypes);
        reportTypeComboBox.setValue("Appointment Summary");
    }

    private void configureContactComboBox() {
        contactComboBox.setItems(contacts);
    }

    private void configureAppointmentsReportTable() {

    }

    private void configureContactScheduleReportTable() {
    }

    private void configureCustomerAuditReportTable() {

    }

    @FXML
    private TableView<AppointmentSummaryReport> appointmentsReportTable;

    @FXML
    private TableColumn<AppointmentSummaryReport, String> apptMonthCol;

    @FXML
    private TableColumn<AppointmentSummaryReport, String> apptTypeCol;

    @FXML
    private TableColumn<AppointmentSummaryReport, Integer> apptTotalCol;

    @FXML
    private TableView<ScheduleReport> scheduleTable;

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
    private ComboBox<Contact> contactComboBox;

    @FXML
    void handleContactComboBoxAction(ActionEvent event) {

    }

    @FXML
    void handleReportTypeComboBoxAction(ActionEvent event) {

    }
}
