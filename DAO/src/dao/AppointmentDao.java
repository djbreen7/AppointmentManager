package dao;

import model.Appointment;

import java.util.List;

public interface AppointmentDao {
    void upsertAppointment(Appointment appointment);

    List<Appointment> getAppointmentsByUserId(int userId);

    List<Appointment> getAppointmentsByCustomerId(int customerId);

    Appointment getAppointment(int appointmentId);

    boolean deleteAppointment(int appointmentId);
}
