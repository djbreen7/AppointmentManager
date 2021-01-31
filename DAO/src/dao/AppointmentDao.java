package dao;

import model.Appointment;

import java.util.List;

public interface AppointmentDao {
    public void upsertAppointment(Appointment appointment);
    public List<Appointment> getAllAppointments(int userId);
    public Appointment getAppointment(int appointmentId);
    public void deleteAppointment(int appointmentId);
}
