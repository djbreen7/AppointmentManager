package application.appointments;

import implementations.AppointmentDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import managers.DataManager;
import managers.SceneManager;
import managers.UserManager;
import model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class ScheduleAppointmentController implements Initializable {
    private AppointmentDaoImpl appointmentDao;
    private UserManager userManager;
    private SceneManager sceneManager;
    private DataManager dataManager;
    private ObservableList<Appointment> appointments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentDao = new AppointmentDaoImpl();
        userManager = UserManager.getInstance();
        sceneManager = SceneManager.getInstance();
        dataManager = DataManager.getInstance();
        appointments = FXCollections.observableList(appointmentDao.getAllAppointments());

        System.out.println(dataManager.getAndClearDataId());
    }
}
