package application.appointments;

import implementations.AppointmentDaoImpl;
import interfaces.AppointmentDao;
import managers.DataManager;
import managers.SceneManager;
import managers.UserManager;
import model.Appointment;

import java.util.List;

public class AppointmentsController {
    private AppointmentDao appointmentDao;
    private UserManager userManager;
    private SceneManager sceneManager;
    private DataManager dataManager;
    private List<Appointment> appointments;

    public void initialize() {
        appointmentDao = new AppointmentDaoImpl();
        userManager = UserManager.getInstance();
        sceneManager = SceneManager.getInstance();
        dataManager = DataManager.getInstance();
        appointments = appointmentDao.getAllAppointments();

        System.out.println(dataManager.getAndClearDataId());
    }
}
