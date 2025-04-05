package Eureka.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Eureka.models.GameData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;



public class ChoseGameModeController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btn_modebasic;

    @FXML 
    private Button btn_back;
    @FXML
    private Button btn_modesurvival;
    @FXML
    private Button btn_multilocal;
    @FXML
    private Button btn_modetimer;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SoundEffects.addSound(btn_modebasic);
        SoundEffects.addSound(btn_back);
    

        btn_modebasic.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                SoundEffects.clickSound.play();
                GameData.setMode("Basic");
                AfficherThemeChooser();
            }
        });

        btn_modesurvival.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                SoundEffects.clickSound.play();
                GameData.setMode("Survival");
                AfficherThemeChooser();
            }
        });

        btn_back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                SoundEffects.clickSound.play();
                DbController.changeScene(arg0, "Loggedin.fxml");
            }
        });

        btn_multilocal.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                SoundEffects.clickSound.play();
                GameData.setMode("Local");
                AfficherThemeChooser();
            }
        });

        btn_modetimer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                SoundEffects.clickSound.play();
                GameData.setMode("Timer");
                AfficherThemeChooser();
            }
        });


    }


    public void AfficherThemeChooser() {
        try {
            AnchorPane settings = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/ThemeChooser.fxml"));
            settings.setLayoutX((root.getWidth() - settings.getPrefWidth()) / 2);
            settings.setLayoutY((root.getHeight() - settings.getPrefHeight()) / 2);
            root.getChildren().add(settings);
            settings.requestFocus();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}