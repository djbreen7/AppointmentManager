package reporting;

import model.AppointmentSummaryReport;
import model.Customer;
import model.ScheduleReport;

import java.util.List;

public interface Reporter {
    List<ScheduleReport> getScheduleReport(int contactId);

    List<AppointmentSummaryReport> getSummaryReport();

    List<Customer> getCustomerAuditReport();
}
