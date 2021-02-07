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
     * @return The schedule for the contact with the provided Contact ID
     */
    List<ScheduleReport> getScheduleReport(int contactId);

    /**
     * Generates a report with a summary of appointments grouped by month and type.
     *
     * @return Appointments grouped by month and type with a total for each group.
     */
    List<AppointmentSummaryReport> getSummaryReport();

    /**
     * Generates a report consisting of customers and information about the last user to update
     * or create them. Most recently updated customers will be first on the report.
     *
     * @return All customers including audit properties.
     */
    List<Customer> getCustomerAuditReport();
}
