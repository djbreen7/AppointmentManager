package dao;

import model.Appointment;

import java.util.List;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface AppointmentDao {
    void upsertAppointment(Appointment appointment);

    List<Appointment> getAppointmentsByUserId(int userId);

    List<Appointment> getAppointmentsByCustomerId(int customerId);

    Appointment getAppointment(int appointmentId);

    boolean deleteAppointment(int appointmentId);
}
