package application.appointments;

import application.ReceivesDataController;
import implementations.AppointmentDaoImpl;
import interfaces.AppointmentDao;
import managers.UserManager;
import model.Appointment;

import java.util.List;

public class AppointmentsController implements ReceivesDataController {
    private AppointmentDao appointmentDao;
    private UserManager userManager;
    private List<Appointment> appointments;

    public void initialize() {
        appointmentDao = new AppointmentDaoImpl();
        userManager = UserManager.getInstance();
        appointments = appointmentDao.getAllAppointments();
    }

    @Override
    public void initData(int id) {

    }
}
