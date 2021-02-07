package reporting;

import model.AppointmentSummaryReport;
import model.Customer;
import model.ScheduleReport;

import java.util.List;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface Reporter {
    List<ScheduleReport> getScheduleReport(int contactId);

    List<AppointmentSummaryReport> getSummaryReport();

    List<Customer> getCustomerAuditReport();
}
