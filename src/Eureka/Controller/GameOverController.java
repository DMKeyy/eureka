package Eureka.Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameOverController {

    @FXML
    private AnchorPane root;
    @FXML
    private Button btn_again, btn_leave;
    @FXML 
    private Label lbl_score;
    @FXML
    private Label lbl_message;

    public void initialize() {
        
        SoundEffects.addSound(btn_again);
        SoundEffects.addSound(btn_leave);

        btn_again.setOnAction(e -> {
            SoundEffects.clickSound.play();
            try {
            AnchorPane settings = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/ThemeChooser.fxml"));
            settings.setLayoutX((root.getWidth() - settings.getPrefWidth()) / 2);
            settings.setLayoutY((root.getHeight() - settings.getPrefHeight()) / 2);
            root.getChildren().add(settings);
            settings.requestFocus();

            } catch (IOException ex) {
            ex.printStackTrace();
        }
        });
        btn_leave.setOnAction(e -> {
            SoundEffects.clickSound.play();
            DbController.changeScene(e, "ChoseGameMode.fxml");
        });

    }

    public void setScore(int score) {
        lbl_score.setText(String.valueOf(score));
    }

    public void setGameOverMessage(String message) {
        if (lbl_message != null) {
            lbl_message.setText(message);
        }
    }
}
