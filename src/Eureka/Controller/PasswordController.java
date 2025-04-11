package Eureka.Controller;

import Eureka.models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PasswordController {

    @FXML private AnchorPane passwordPane;
    @FXML private TextField passwordField;
    @FXML private Button btn_confirm;
    @FXML private Button btn_cancel;

    private ProfileController profileController;

    @FXML
    public void initialize() {
        btn_confirm.setOnAction(e -> {
            Player player = Player.getCurrentPlayer();
            if (player == null) return;

            String newPassword = passwordField.getText().trim();

            if (!newPassword.isEmpty()) {
                boolean success = DbController.updatePassword(player.getUsername(), newPassword);

                if (success) {
                    player.setPassword(newPassword); 

                    if (profileController != null) {
                        profileController.updatePasswordLabel();
                    }

                
                    closeOverlay();
                } else {
                    DbController.showAlert(Alert.AlertType.ERROR, "Error: Could not update password in database.");
                }
            } else {
                DbController.showAlert(Alert.AlertType.WARNING, "⚠ The password cannot be empty!");
            }
        });

        btn_cancel.setOnAction(e -> closeOverlay());
    }


    private void closeOverlay() {
        ((AnchorPane) passwordPane.getParent()).getChildren().remove(passwordPane);
    }


    public void setProfileController(ProfileController controller) {
    this.profileController = controller;
    }

}
