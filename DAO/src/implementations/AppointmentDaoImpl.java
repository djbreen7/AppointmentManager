package implementations;

import dao.AppointmentDao;
import data.DatabaseConnection;
import model.Appointment;
import utilities.ResultSetBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDaoImpl implements AppointmentDao {

    private ResultSetBuilder resultSetBuilder;

    public AppointmentDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

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
                new Timestamp(appointment.getStart().getTime().getTime()),
                new Timestamp(appointment.getEnd().getTime().getTime()),
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
                "SELECT * FROM appointments a " +
                "JOIN contacts c ON c.Contact_ID = a.Contact_ID " +
                "WHERE User_ID = %s ", userId
        );
        List<Appointment> appointments = new ArrayList();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                var appointment = resultSetBuilder.buildAppointmentResult(result, false);
                appointment.setContact(resultSetBuilder.buildContactResult(result));
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
        String query = String.format(
                "SELECT * FROM appointments " +
                "WHERE Appointment_ID = %s",
                appointmentId
        );
        Appointment appointment = null;
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                appointment = resultSetBuilder.buildAppointmentResult(result, true);
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
}
