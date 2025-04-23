package Eureka.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Eureka.models.SoundEffects;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LoggedInController implements Initializable {

    @FXML 
    private AnchorPane root;

    @FXML
    private Button btn_logout, btn_settings, quit, btn_about, btn_play, btn_profile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SoundEffects.addSound(btn_logout);
        SoundEffects.addSound(quit);
        SoundEffects.addSound(btn_settings);
        SoundEffects.addSound(btn_about);
        SoundEffects.addSound(btn_play);

        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PauseTransition pause = new PauseTransition(Duration.millis(200));
                pause.setOnFinished(e -> SceneManager.changeScene(event, "LogIn.fxml"));
                pause.play();
            }
        });

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PauseTransition pause = new PauseTransition(Duration.millis(300));
                pause.setOnFinished(e -> System.exit(0));
                pause.play();
            }
        });

        btn_settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    AnchorPane settings = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/Settings.fxml"));
                    settings.setLayoutX((root.getWidth() - settings.getPrefWidth()) / 2);
                    settings.setLayoutY((root.getHeight() - settings.getPrefHeight()) / 2);

                    settings.setScaleX(0);
                    settings.setScaleY(0);

                    root.getChildren().add(settings);
                    settings.requestFocus();

                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), settings);
                    scaleTransition.setFromX(0);
                    scaleTransition.setFromY(0);
                    scaleTransition.setToX(1);
                    scaleTransition.setToY(1);
                    scaleTransition.play();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    AnchorPane about = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/about.fxml"));
                    about.setLayoutX((root.getWidth() - about.getPrefWidth()) / 2);
                    about.setLayoutY((root.getHeight() - about.getPrefHeight()) / 2);
                    root.getChildren().add(about);
                    about.requestFocus();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode().toString().equals("ESCAPE")) {
                    root.getChildren().removeIf(node -> node instanceof AnchorPane);
                }
            }
        });

        btn_play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PauseTransition pause = new PauseTransition(Duration.millis(200));
                pause.setOnFinished(e -> SceneManager.changeScene(event, "ChoseGameMode.fxml"));
                pause.play();
            }
        });

        btn_profile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PauseTransition pause = new PauseTransition(Duration.millis(200));
                pause.setOnFinished(e -> SceneManager.changeScene(event, "Profile.fxml"));
                pause.play();
            }
        }); 
    }
}