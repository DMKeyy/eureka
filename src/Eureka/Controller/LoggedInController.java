package Eureka.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LoggedInController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btn_logout,btn_settings,quit,btn_about;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SoundEffects.addSound(btn_logout);
        SoundEffects.addSound(quit);
        SoundEffects.addSound(btn_settings);
        SoundEffects.addSound(btn_about);

        btn_logout.setOnAction(event -> {
            SoundEffects.clickSound.play(); // Play sound
            PauseTransition pause = new PauseTransition(Duration.millis(200)); // Wait 300ms
            pause.setOnFinished(e -> DbController.changeScene(event, "LogIn.fxml")); // Change scene after sound finishes
            pause.play();
        });

        quit.setOnAction(event -> {
            SoundEffects.clickSound.play();
            PauseTransition pause = new PauseTransition(Duration.millis(300));
            pause.setOnFinished(e -> System.exit(0));
            pause.play();
        });

        btn_settings.setOnAction(event -> {
            try {
                SoundEffects.clickSound.play();

                AnchorPane settings = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/Settings.fxml"));
                settings.setLayoutX((root.getWidth() - settings.getPrefWidth()) / 2);
                settings.setLayoutY((root.getHeight() - settings.getPrefHeight()) / 2);
                root.getChildren().add(settings);
                settings.requestFocus();
        
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_about.setOnAction(event -> {
            try {
                SoundEffects.clickSound.play();

                AnchorPane about = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/about.fxml"));
                about.setLayoutX((root.getWidth() - about.getPrefWidth()) / 2);
                about.setLayoutY((root.getHeight() - about.getPrefHeight()) / 2);
                root.getChildren().add(about);
                about.requestFocus();
        
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        root.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ESCAPE")) {
                root.getChildren().removeIf(node -> node instanceof AnchorPane);
                SoundEffects.clickSound.play();
            }
        });   
    }
}
