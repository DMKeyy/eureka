package Eureka.Controller;

import Eureka.models.Badge;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameOverController {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btn_again, btn_leave, btn_revision;

    @FXML
    private Label lbl_score;

    @FXML
    private Label lbl_message, lbl_badge; // Optionnel pour message "Joueur 1 a perdu"

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

        btn_revision.setOnAction(e -> {
            SoundEffects.clickSound.play(); 
            DbController.changeScene(e, "ModeRevision.fxml");
        });
    }

    public void setScore(int score, List<Badge> newBadges) {
        lbl_score.setText(String.valueOf(score));
    
        if (newBadges != null && !newBadges.isEmpty()) {
            String msg = "🎉 Tu as gagné " + newBadges.size() + " badge";
            if (newBadges.size() > 1) msg += "s";
            msg += " !";
            lbl_badge.setText(msg);
        } else {
            lbl_badge.setText(""); 
        }
    }
    
    


    public void setGameOverMessage(String message) {
        if (lbl_message != null) {
            lbl_message.setText(message);
        }
    }
}
