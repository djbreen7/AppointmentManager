package reporting;

import data.DatabaseConnection;
import model.AppointmentSummaryReport;
import model.Customer;
import model.ScheduleReport;
import utilities.ResultSetBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * A unbiased database query-er for reporting.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class ReporterImpl implements Reporter {
    private final ResultSetBuilder resultSetBuilder;

    public ReporterImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

    /**
     * Generates a Contact Schedule report.
     *
     * @param contactId The FK Contact ID to match.
     * @return List<ScheduleReport>
     */
    @Override
    public List<ScheduleReport> getScheduleReport(int contactId) {
        var query = String.format(
                "SELECT * FROM appointments a " +
                        "WHERE a.Contact_ID = %s", contactId
        );
        List<ScheduleReport> scheduleReport = new ArrayList<>();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                var schedule = resultSetBuilder.buildScheduleReport(result);
                scheduleReport.add(schedule);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return scheduleReport;
        }
    }

    /**
     * Generates a report with a summary of appointments grouped by month and type.
     *
     * @return List<AppointmentSummaryReport>
     */
    @Override
    public List<AppointmentSummaryReport> getSummaryReport() {
        var query = "SELECT MONTHNAME(Start), Type, count(*) " +
                "FROM appointments " +
                "GROUP BY MONTHNAME(Start), Type";
        List<AppointmentSummaryReport> summary = new ArrayList<>();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                var item = resultSetBuilder.buildAppointmentSummaryReport(result);
                summary.add(item);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return summary;
        }
    }

    /**
     * Generates a report consisting of customers and information about the last user to update
     * or create them. Most recently updated customers will be first on the report.
     *
     * @return List<Customer>
     */
    @Override
    public List<Customer> getCustomerAuditReport() {
        var query = "SELECT * FROM customers ORDER BY Last_Update desc";
        List<Customer> report = new ArrayList<>();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                var customer = resultSetBuilder.buildCustomerResult(result, true);
                report.add(customer);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return report;
        }
    }
}
