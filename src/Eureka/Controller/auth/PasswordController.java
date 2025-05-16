package Eureka.Controller.auth;

import Eureka.Controller.ui.ProfileController;
import Eureka.Controller.ui.SceneManager;
import Eureka.models.SoundEffects;
import Eureka.models.PlayerRep.Player;
import Eureka.models.PlayerRep.PlayerRepository;
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

        SoundEffects.addSound(btn_confirm);
        SoundEffects.addSound(btn_cancel);

        
        btn_confirm.setOnAction(e -> {
            Player player = Player.getCurrentPlayer();
            if (player == null) return;

            String newPassword = passwordField.getText().trim();

            if (!newPassword.isEmpty()) {
                boolean success = PlayerRepository.updatePassword(player.getUsername(), newPassword);

                if (success) {
                    player.setPassword(newPassword);            
                    SceneManager.changeScene(e, "Profile.fxml");
                } else {
                    SceneManager.showAlert(Alert.AlertType.ERROR, "Error: Could not update password in database.");
                }
            } else {
                SceneManager.showAlert(Alert.AlertType.WARNING, "⚠ The password cannot be empty!");
            }
        });

        btn_cancel.setOnAction(e -> SceneManager.changeScene(e, "Profile.fxml"));
    }



    public void setProfileController(ProfileController controller) {
    this.profileController = controller;
    }

}
