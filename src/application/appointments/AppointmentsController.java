package application.appointments;

import implementations.AppointmentDaoImpl;
import interfaces.AppointmentDao;
import managers.UserManager;

public class AppointmentsController {
    private AppointmentDao appointmentDao;
    private UserManager userManager;

    public void initialize() {
        System.out.println("Initializing...");
        appointmentDao = new AppointmentDaoImpl();
        userManager = UserManager.getInstance();

        var appointments = appointmentDao.getAllAppointments();
    }
}
