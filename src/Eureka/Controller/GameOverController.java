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

    public void initialize() {
        
        SoundEffects.addSound(btn_again);
        SoundEffects.addSound(btn_leave);

        
    }

    public void setScore(int score) {
        lbl_score.setText(""+score);
    }
}
