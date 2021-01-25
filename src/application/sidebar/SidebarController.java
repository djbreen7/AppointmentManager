package application.sidebar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import managers.SceneManager;

import java.util.Arrays;
import java.util.List;

public class SidebarController {
    private SceneManager sceneManager = SceneManager.getInstance();
    private List<Button> buttons;

    public void initialize() {
        buttons = Arrays.asList(appointmentsBtn, customersBtn);
        appointmentsBtn.getStyleClass().add("active");

    }

    private void setActiveButton(String activeButtonText) {
        buttons.forEach(button -> {
            if (button.getText().equals(activeButtonText)) {
                button.getStyleClass().add("active");
                System.out.print(button.getStyleClass());
                return;
            }
            button.getStyleClass().remove("active");
        });
    };

    @FXML
    private Button appointmentsBtn;

    @FXML
    private Button customersBtn;

    @FXML
    void handleAppointmentsBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.APPOINTMENTS_SCENE);
        setActiveButton(appointmentsBtn.getText());
    }

    @FXML
    void handleCustomersBtnAction(ActionEvent event) {
        sceneManager.goToScene(sceneManager.CUSTOMERS_SCENE);
        setActiveButton(customersBtn.getText());

    }
}
