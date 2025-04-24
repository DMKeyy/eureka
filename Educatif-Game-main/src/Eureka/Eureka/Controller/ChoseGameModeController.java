package Eureka.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import Eureka.models.GameData;

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
    private Button btn_modelocal;

    @FXML
    private Button btn_modetimer;

    @FXML
    private Button btn_profile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SoundEffects.addSound(btn_modebasic);
        SoundEffects.addSound(btn_back);
        SoundEffects.addSound(btn_modesurvival);
        SoundEffects.addSound(btn_modelocal);

        // Mode Basic
        btn_modebasic.setOnAction(event -> {
            SoundEffects.clickSound.play();
            GameData.setMode("Basic");
            loadThemeChooser();
        });

        // Mode Survival
        btn_modesurvival.setOnAction(event -> {
            SoundEffects.clickSound.play();
            GameData.setMode("Survival");
            loadThemeChooser();
        });

        // Mode Local Multiplayer
        btn_modelocal.setOnAction(event -> {
            SoundEffects.clickSound.play();
            GameData.setMode("Local");
            loadThemeChooser();
        });

        btn_modetimer.setOnAction(event -> {

                SoundEffects.clickSound.play();
                GameData.setMode("Timer");
                loadThemeChooser();
            
        });

        btn_profile.setOnAction(event -> {
            SoundEffects.clickSound.play();
            DbController.changeScene(event, "Profile.fxml");
        });

        // Retour
        btn_back.setOnAction(event -> {
            SoundEffects.clickSound.play();
            DbController.changeScene(event, "Loggedin.fxml");
        });
    }

    private void loadThemeChooser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Eureka/View/fxml/ThemeChooser.fxml"));
            AnchorPane settings = loader.load();

            settings.setLayoutX((root.getWidth() - settings.getPrefWidth()) / 2);
            settings.setLayoutY((root.getHeight() - settings.getPrefHeight()) / 2);
            root.getChildren().add(settings);
            settings.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

