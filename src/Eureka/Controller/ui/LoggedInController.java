package Eureka.Controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import Eureka.models.SoundEffects;
import Eureka.models.PlayerRep.Player;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LoggedInController implements Initializable {

    @FXML 
    private AnchorPane root;

    @FXML
    private Button btn_logout, btn_settings, quit, btn_about, btn_play;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(Player.getCurrentPlayer().toString());

        SoundEffects.addSound(btn_logout);
        SoundEffects.addSound(quit);
        SoundEffects.addSound(btn_settings);
        SoundEffects.addSound(btn_about);
        SoundEffects.addSound(btn_play);

        btn_logout.setOnAction(event -> {
            PauseTransition pause = new PauseTransition(Duration.millis(200));
            pause.setOnFinished(_ -> SceneManager.changeScene(event, "LogIn.fxml"));
            pause.play();
        });

        quit.setOnAction(_ -> {
            PauseTransition pause = new PauseTransition(Duration.millis(300));
            pause.setOnFinished(_ -> System.exit(0));
            pause.play();
        });

        btn_settings.setOnAction(_ -> {
            SceneManager.showPopup(root, "Settings.fxml");
        });

        btn_about.setOnAction(_ -> {
            SceneManager.showPopup(root, "about.fxml");
        });

        root.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ESCAPE")) {
                root.getChildren().removeIf(node -> node instanceof AnchorPane);
            }
        });

        btn_play.setOnAction(event -> {
            PauseTransition pause = new PauseTransition(Duration.millis(200));
            pause.setOnFinished(_ -> SceneManager.changeScene(event, "ChoseGameMode.fxml"));
            pause.play();
        });
    }
}