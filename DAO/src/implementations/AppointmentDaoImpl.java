package implementations;

import dao.AppointmentDao;
import data.DatabaseConnection;
import model.Appointment;
import utilities.ResultSetBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for working with Appointments in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class AppointmentDaoImpl implements AppointmentDao {
    private final ResultSetBuilder resultSetBuilder;

    public AppointmentDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

    /**
     * Insert or update an appointment in the database.
     *
     * @param appointment Appointment to be updated or inserted.
     */
    @Override
    public void upsertAppointment(Appointment appointment) {
        if (appointment.getAppointmentId() == 0) {
            addAppointment(appointment);
            return;
        }
        updateAppointment(appointment);
    }

    /**
     * Insert an appointment into the database.
     *
     * @param appointment Appointment to be inserted.
     */
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
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
        }
    }

    /**
     * Update an appointment in the database.
     *
     * @param appointment The appointment to be updated.
     */
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
                new Timestamp(appointment.getStart().getTime().getTime()),
                new Timestamp(appointment.getEnd().getTime().getTime()),
                appointment.getLastUpdatedBy(),
                appointment.getCustomerId(),
                appointment.getUserId(),
                appointment.getContactId(),
                appointment.getAppointmentId()
        );
        try {
            DatabaseConnection.makeConnection();
            DatabaseConnection.connection
                    .createStatement()
                    .execute(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
        }
    }

    /**
     * Get appointments by FK User ID from the database.
     *
     * @param userId The User ID to match.
     * @return Appointments with the User ID.
     */
    @Override
    public List<Appointment> getAppointmentsByUserId(int userId) {
        String query = String.format(
                "SELECT * FROM appointments a " +
                        "JOIN contacts c ON c.Contact_ID = a.Contact_ID " +
                        "WHERE User_ID = %s ", userId
        );
        List<Appointment> appointments = new ArrayList<>();
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
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return appointments;
        }
    }

    /**
     * Get appointments by FK Customer ID from the database.
     *
     * @param customerId The Customer ID to match.
     * @return Appointments with the Customer ID
     */
    @Override
    public List<Appointment> getAppointmentsByCustomerId(int customerId) {
        String query = String.format(
                "SELECT * FROM appointments a " +
                        "WHERE Customer_ID = %s ", customerId
        );
        List<Appointment> appointments = new ArrayList<>();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                var appointment = resultSetBuilder.buildAppointmentResult(result, false);
                appointments.add(appointment);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return appointments;
        }
    }

    /**
     * Get appointment by PK Appointment ID from the database.
     *
     * @param appointmentId The ID of the appointment to get.
     * @return The appointment with the provided ID.
     */
    @Override
    public Appointment getAppointment(int appointmentId) {
        String query = String.format(
                "SELECT * FROM appointments a " +
                        "JOIN contacts c ON c.Contact_ID = a.Contact_ID " +
                        "JOIN customers cust ON cust.Customer_ID = a.Customer_ID " +
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
                appointment.setContact(resultSetBuilder.buildContactResult(result));
                appointment.setCustomer(resultSetBuilder.buildCustomerResult(result, false));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return appointment;
        }
    }

    /**
     * Delete appointment by PK Appointment ID from the database.
     *
     * @param appointmentId The ID of the appointment to delete.
     * @return A boolean indicating if the deletion was successful or not.
     */
    @Override
    public boolean deleteAppointment(int appointmentId) {
        var isSuccess = false;
        String query = String.format(
                "DELETE FROM appointments where Appointment_ID = %s", appointmentId
        );
        try {
            DatabaseConnection.makeConnection();
            DatabaseConnection.connection
                    .createStatement()
                    .execute(query);
            isSuccess = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return isSuccess;
        }
    }
}
