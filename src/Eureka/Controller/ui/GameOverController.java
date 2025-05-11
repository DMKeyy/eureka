package Eureka.Controller.ui;

import java.util.List;

import Eureka.models.GameData;
import Eureka.models.SoundEffects;
import Eureka.models.BadgeRep.Badge;
import Eureka.models.PlayerRep.Player;
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
    private Label lbl_message;

    public void initialize() {
        
        SoundEffects.addSound(btn_again);
        SoundEffects.addSound(btn_leave);
        SoundEffects.addSound(btn_revision);

        List<Badge>Badgeswon = Badge.checkAndAssignBadges(Player.getCurrentPlayer());
        if (!Badgeswon.isEmpty()) {
            StringBuilder message = new StringBuilder("Bravo ! Vous avez gagné les badges suivants :\n");
            for (Badge badge : Badgeswon) {
                message.append(badge.getName()).append("\n");
            }
            lbl_message.setText(message.toString());
        }
        lbl_score.setText(""+GameData.getScore());
        GameData.setScore(0);

        btn_again.setOnAction(_ -> {
            SceneManager.showPopup(root, "ThemeChooser.fxml");
        });

        btn_leave.setOnAction(e -> {
            SceneManager.changeScene(e, "ChoseGameMode.fxml");
        });

        btn_revision.setOnAction(e -> {
            SceneManager.changeScene(e, "ModeRevision.fxml");
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
