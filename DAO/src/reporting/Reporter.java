package reporting;

import model.AppointmentSummaryReport;
import model.Customer;
import model.ScheduleReport;

import java.util.List;

/**
 * A unbiased database query-er for reporting.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface Reporter {
    /**
     * Generates a Contact Schedule report.
     *
     * @param contactId The FK Contact ID to match.
     * @return List of ScheduleReport
     */
    List<ScheduleReport> getScheduleReport(int contactId);

    /**
     * Generates a report with a summary of appointments grouped by month and type.
     *
     * @return List of AppointmentSummaryReport
     */
    List<AppointmentSummaryReport> getSummaryReport();

    /**
     * Generates a report consisting of customers and information about the last user to update
     * or create them. Most recently updated customers will be first on the report.
     *
     * @return List of Customer
     */
    List<Customer> getCustomerAuditReport();
}
