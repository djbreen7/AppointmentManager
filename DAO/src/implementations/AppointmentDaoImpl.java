package implementations;

import calendar.CalendarUtils;
import data.DatabaseConnection;
import interfaces.AppointmentDao;
import model.Appointment;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDaoImpl implements AppointmentDao {
    @Override
    public List<Appointment> getAllAppointments() {
        String query = String.format("SELECT * FROM appointments");
        List<Appointment> appointments = new ArrayList();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                var appointment = buildResult(result);
                appointments.add(appointment);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
            return appointments;
        }
    }

    @Override
    public Appointment getAppointment(int appointmentId) {
        String query = String.format("SELECT * FROM appointments");
        Appointment appointment = null;
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                appointment = buildResult(result);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
            return appointment;
        }
    }

    @Override
    public void updateAppointment(Appointment appointment) {

    }

    @Override
    public void deleteAppointment(int appointmentId) {

    }

    private Appointment buildResult(ResultSet result) throws Exception {
        var appointmentId = result.getInt("Appointment_ID");
        var customerId = result.getInt("Customer_ID");
        var userId = result.getInt("User_ID");
        var contactId = result.getInt("Contact_ID");
        var title = result.getString("Title");
        var description = result.getString("Description");
        var location = result.getString("Location");
        var type = result.getString("Type");
        var start = CalendarUtils.toCalendar(result.getDate("Start"));
        var end = CalendarUtils.toCalendar(result.getDate("End"));
        var createDate = CalendarUtils.toCalendar(result.getDate("Create_Date"));
        var createdBy = result.getString("Created_By");
        var lastUpdate = CalendarUtils.toCalendar(result.getDate("Last_Update"));
        var lastUpdatedBy = result.getString("Last_Updated_By");

        return new Appointment(appointmentId,
                customerId,
                userId,
                contactId,
                title,
                description,
                location,
                type,
                start,
                end,
                createDate,
                createdBy,
                lastUpdate,
                lastUpdatedBy);
    }
}
