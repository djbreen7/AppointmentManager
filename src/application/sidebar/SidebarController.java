package application.sidebar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import managers.SceneManager;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class SidebarController {
    private SceneManager sceneManager = SceneManager.getInstance();
    private List<Button> buttons;

    public void initialize() {
        buttons = Arrays.asList(appointmentsBtn, customersBtn, reportingBtn);
        appointmentsBtn.getStyleClass().add("active");
    }

    /**
     * Makes the button related to the active scene appear bold.
     *
     * @param activeButtonText Text matching the button text.
     */
    private void setActiveButton(String activeButtonText) {
        buttons.forEach(button -> {
            button.getStyleClass().remove("active");
            if (button.getText().equals(activeButtonText)) {
                button.getStyleClass().add("active");
            }
        });
    }

    @FXML
    private Button appointmentsBtn;

    @FXML
    private Button customersBtn;

    @FXML
    private Button reportingBtn;

    @FXML
    private void handleAppointmentsBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.APPOINTMENTS_SCENE);
        setActiveButton(appointmentsBtn.getText());
    }

    @FXML
    private void handleCustomersBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.CUSTOMERS_SCENE);
        setActiveButton(customersBtn.getText());
    }

    @FXML
    private void handleReportingBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.REPORTING_SCENE);
        setActiveButton(reportingBtn.getText());
    }
}
