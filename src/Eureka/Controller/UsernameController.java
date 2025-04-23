package Eureka.Controller;

import Eureka.models.SoundEffects;
import Eureka.models.PlayerRep.Player;
import Eureka.models.PlayerRep.PlayerRepository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class UsernameController {

    @FXML private AnchorPane usernamePane;
    @FXML private TextField usernameField;
    @FXML private Button btn_confirm;
    @FXML private Button btn_cancel;

    private ProfileController profileController;

    public void setProfileController(ProfileController controller) {
        this.profileController = controller;
    }

    @FXML
    public void initialize() {

        SoundEffects.addSound(btn_confirm);
        SoundEffects.addSound(btn_cancel);
        
        Player player = Player.getCurrentPlayer();
        if (player != null) {
            usernameField.setText(player.getUsername());
        }

        btn_confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            String newUsername = usernameField.getText().trim();
            if (!newUsername.isEmpty()) {
                boolean success = PlayerRepository.updateUsername(player.getUsername(), newUsername);
                if (success) {
                player.setUsername(newUsername);

                if (profileController != null) {
                    profileController.updateUsernameLabel();
                }

                closeOverlay();
                } else {
                SceneManager.showAlert(Alert.AlertType.ERROR, "❌ Couldn't update username.");
                }
            } else {
                SceneManager.showAlert(Alert.AlertType.WARNING, "⚠ Username cannot be empty.");
            }
            }
        });

        btn_cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            closeOverlay();
            }
        });
    }

    private void closeOverlay() {
        ((AnchorPane) usernamePane.getParent()).getChildren().remove(usernamePane);
    }
}