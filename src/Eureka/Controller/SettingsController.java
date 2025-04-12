package Eureka.Controller;

import Eureka.models.SoundEffects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class SettingsController {
   
    @FXML 
    private AnchorPane settingsPane;
    @FXML
    private Button btn_back, btn_sound, btn_leaderboard, btn_profile, btn_help;

    private Parent leaderboardOverlay;
    private Parent helpOverlay;

    @FXML
    public void initialize() {
        // Add sound effects to buttons
        SoundEffects.addSound(btn_back);
        SoundEffects.addSound(btn_sound);
        SoundEffects.addSound(btn_leaderboard);
        SoundEffects.addSound(btn_profile);
        SoundEffects.addSound(btn_help);
        if (SoundEffects.isMuted()) {
            btn_sound.setText("Unmute");
        } else {
            btn_sound.setText("Mute");
        }
        
        // Initialize button actions
        setupBackButton();
        setupSoundButton();
        setupLeaderboardButton();
        setupHelpButton();
    }

 
    private void setupBackButton() {
        btn_back.setOnAction(event -> {
            DbController.changeScene(event, "LoggedIn.fxml");
        });
    }

    private void setupSoundButton() {
        btn_sound.setOnAction(event -> {
            SoundEffects.toggleMute();
            btn_sound.setText(SoundEffects.isMuted() ? "Unmute" : "Mute");
        });
    }

    private void setupLeaderboardButton() {
        btn_leaderboard.setOnAction(event -> {
            try {
                if (leaderboardOverlay == null) {
                    leaderboardOverlay = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/Leaderboard.fxml"));
                }
                showOverlay(leaderboardOverlay);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupHelpButton() {
        btn_help.setOnAction(event -> {
            try {
                if (helpOverlay == null) {
                    helpOverlay = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/Help.fxml"));
                }
                showOverlay(helpOverlay);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void showOverlay(Parent overlay) {
        // Create a new StackPane to hold the overlay
        StackPane overlayContainer = new StackPane(overlay);
        overlayContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        
        // Add close on click outside overlay
        overlayContainer.setOnMouseClicked(event -> {
            if (event.getTarget() == overlayContainer) {
                settingsPane.getChildren().remove(overlayContainer);
            }
        });

        // Add the overlay to the settings pane
        settingsPane.getChildren().add(overlayContainer);
        
        // Set the overlay to fill the entire pane
        AnchorPane.setTopAnchor(overlayContainer, 0.0);
        AnchorPane.setBottomAnchor(overlayContainer, 0.0);
        AnchorPane.setLeftAnchor(overlayContainer, 0.0);
        AnchorPane.setRightAnchor(overlayContainer, 0.0);
    }
}
