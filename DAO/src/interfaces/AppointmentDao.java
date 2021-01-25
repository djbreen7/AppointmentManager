package interfaces;

import model.Appointment;

import java.util.List;

public interface AppointmentDao {
    public List<Appointment> getAllAppointments();
    public Appointment getAppointment(int appointmentId);
    public void updateAppointment(Appointment appointment);
    public void deleteAppointment(int appointmentId);
}
