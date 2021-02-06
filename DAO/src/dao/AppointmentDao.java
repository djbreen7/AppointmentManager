package dao;

import model.Appointment;

import java.util.List;

public interface AppointmentDao {
    public void upsertAppointment(Appointment appointment);
    public List<Appointment> getAppointmentsByUserId(int userId);
    public List<Appointment> getAppointmentsByCustomerId(int customerId);
    public Appointment getAppointment(int appointmentId);
    public boolean deleteAppointment(int appointmentId);
}
