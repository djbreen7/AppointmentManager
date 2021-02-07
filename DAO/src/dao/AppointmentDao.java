package dao;

import model.Appointment;

import java.util.List;

/**
 * DAO for working with Appointments in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface AppointmentDao {
    void upsertAppointment(Appointment appointment);

    /**
     * Get appointments by FK User ID from the database.
     *
     * @param userId The User ID to match.
     * @return Appointments with the User ID.
     */
    List<Appointment> getAppointmentsByUserId(int userId);

    /**
     * Get appointments by FK Customer ID from the database.
     *
     * @param customerId The Customer ID to match.
     * @return Appointments with the Customer ID.
     */
    List<Appointment> getAppointmentsByCustomerId(int customerId);

    /**
     * Get appointment by PK Appointment ID from the database.
     *
     * @param appointmentId The ID of the appointment to get.
     * @return The appointment with the provided ID.
     */
    Appointment getAppointment(int appointmentId);

    /**
     * Delete appointment by PK Appointment ID from the database.
     *
     * @param appointmentId The ID of the appointment to delete.
     * @return A boolean indicating if the deletion was successful or not.
     */
    boolean deleteAppointment(int appointmentId);
}
