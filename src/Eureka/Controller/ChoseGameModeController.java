package Eureka.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Eureka.models.GameData;
import Eureka.models.SoundEffects;
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
    @FXML
    private Button btn_modeprogressivetimetrial;
    @FXML
    private Button btn_modemissingletter;
    @FXML
    private Button btn_modemcq;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SoundEffects.addSound(btn_modebasic);
        SoundEffects.addSound(btn_back);
        SoundEffects.addSound(btn_modesurvival);
        SoundEffects.addSound(btn_multilocal);
        SoundEffects.addSound(btn_modetimer);
        SoundEffects.addSound(btn_modeprogressivetimetrial);
        SoundEffects.addSound(btn_modemissingletter);
        SoundEffects.addSound(btn_modemcq);
        
    

        btn_modebasic.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                SoundEffects.clickSound.play();
                GameData.setMode("Basic");
                SceneManager.showPopup(root, "ThemeChooser.fxml");
            }
        });

        btn_modesurvival.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                GameData.setMode("Survival");
                SceneManager.showPopup(root, "ThemeChooser.fxml");
            }
        });

        btn_back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                SceneManager.changeScene(arg0, "Loggedin.fxml");
            }
        });

        btn_multilocal.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                GameData.setMode("Local");
                SceneManager.showPopup(root, "ThemeChooser.fxml");
            }
        });

        btn_modetimer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                GameData.setMode("Timer");
                SceneManager.showPopup(root, "ThemeChooser.fxml");
            }
        });

        btn_modeprogressivetimetrial.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                GameData.setMode("ProgressiveTimeTrial");
                SceneManager.showPopup(root, "ThemeChooser.fxml");
            }
        });

        btn_modemissingletter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                GameData.setMode("MissingLetter");
                SceneManager.showPopup(root, "ThemeChooser.fxml");
            }
        });

        btn_modemcq.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                GameData.setMode("Mcq");
                SceneManager.showPopup(root, "ThemeChooser.fxml");
            }
        });


    }
}