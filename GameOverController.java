package Eureka.Controller;

import javafx.fxml.FXML;
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
    private Label lbl_message; // Optionnel pour message "Joueur 1 a perdu"

    public void initialize() {
        SoundEffects.addSound(btn_again);
        SoundEffects.addSound(btn_leave);

        btn_again.setOnAction(e -> {
            SoundEffects.clickSound.play();
            DbController.changeScene(e, "ChoseGameMode.fxml");
        });

        btn_leave.setOnAction(e -> {
            SoundEffects.clickSound.play();
            DbController.changeScene(e, "Loggedin.fxml");
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
