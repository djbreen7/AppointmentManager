package application.appointments;

import implementations.AppointmentDaoImpl;
import dao.AppointmentDao;
import javafx.fxml.Initializable;
import managers.DataManager;
import managers.SceneManager;
import managers.UserManager;
import model.Appointment;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    private AppointmentDao appointmentDao;
    private UserManager userManager;
    private SceneManager sceneManager;
    private DataManager dataManager;
    private List<Appointment> appointments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentDao = new AppointmentDaoImpl();
        userManager = UserManager.getInstance();
        sceneManager = SceneManager.getInstance();
        dataManager = DataManager.getInstance();
        appointments = appointmentDao.getAllAppointments(userManager.getCurrentUser().getUserId());

        System.out.println(dataManager.getAndClearCustomerId());
    }
}
