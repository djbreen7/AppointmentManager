package implementations;

import dao.AppointmentDao;
import data.DatabaseConnection;
import model.Appointment;
import utilities.ResultSetBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
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
     * @param appointment
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
     * @param appointment
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
     * @param appointment
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
     * @param userId
     * @return List<Appointment>
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
     * @param customerId
     * @return List<Appointment>
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
     * @param appointmentId
     * @return Appointment
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
     * @param appointmentId
     * @return boolean
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
