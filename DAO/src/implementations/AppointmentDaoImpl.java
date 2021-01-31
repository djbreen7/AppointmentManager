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
    public void upsertAppointment(Appointment appointment) {
        if (appointment.getAppointmentId() == -1) {
            addAppointment(appointment);
            return;
        }
        updateAppointment(appointment);
    }

    private void addAppointment(Appointment appointment) {
        String query = String.format(
                "INSERT INTO appointments " +
                "(Title, Description, Location, Type, Start, End, " +
                "Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %s, %s, %s)",
                appointment.getTitle(),
                appointment.getDescription(),
                appointment.getLocation(),
                appointment.getType(),
                appointment.getStart(),
                appointment.getEnd(),
                appointment.getCreatedBy(),
                appointment.getLastUpdatedBy(),
                appointment.getCustomerId(),
                appointment.getUserId(),
                appointment.getContactId()
        );
        try {
            DatabaseConnection.makeConnection();
            DatabaseConnection.connection
                    .createStatement()
                    .execute(query);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
        }
    }

    private void updateAppointment(Appointment appointment) {
        String query = String.format(
                "UPDATE appointments " +
                "SET Title = '%s', " +
                "Description = '%s', " +
                "Location = '%s', " +
                "Type = '%s', " +
                "Start = '%s', " +
                "End = '%s', " +
                "Last_Updated_By = '%s', " +
                "Customer_ID = %s, " +
                "User_ID = %s, " +
                "Contact_ID = %s " +
                "WHERE Appointment_ID = %s",
                appointment.getTitle(),
                appointment.getDescription(),
                appointment.getLocation(),
                appointment.getType(),
                appointment.getStart(),
                appointment.getEnd(),
                appointment.getLastUpdatedBy(),
                appointment.getCustomerId(),
                appointment.getUserId(),
                appointment.getContactId()
        );
        try {
            DatabaseConnection.makeConnection();
            DatabaseConnection.connection
                    .createStatement()
                    .execute(query);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
        }
    }

    @Override
    public List<Appointment> getAllAppointments(int userId) {
        String query = String.format(
                "SELECT * FROM appointments " +
                "WHERE User_ID = %s", userId
        );
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
