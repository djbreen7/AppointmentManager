package implementations;

import dao.AppointmentDao;
import data.DatabaseConnection;
import model.Appointment;
import utilities.CalendarUtils;

import java.sql.ResultSet;
import java.time.LocalDateTime;
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
        String query = String.format("SELECT * FROM appointments WHERE Appointment_ID = %s", appointmentId);
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
        var start = CalendarUtils.fromLocalDateTime(result.getObject("Start", LocalDateTime.class));
        var end = CalendarUtils.fromLocalDateTime(result.getObject("End", LocalDateTime.class));
        var createDate = CalendarUtils.fromLocalDateTime(result.getObject("Create_Date", LocalDateTime.class));
        var createdBy = result.getString("Created_By");
        var lastUpdate = CalendarUtils.fromLocalDateTime(result.getObject("Last_Update", LocalDateTime.class));
        var lastUpdatedBy = result.getString("Last_Updated_By");

        return new Appointment(
                appointmentId,
                customerId,
                null,
                userId,
                null,
                contactId,
                null,
                title,
                description,
                location,
                type,
                start,
                end,
                createDate,
                createdBy,
                lastUpdate,
                lastUpdatedBy
        );
    }
}
