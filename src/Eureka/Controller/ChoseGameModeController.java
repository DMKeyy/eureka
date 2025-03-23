package Eureka.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SoundEffects.addSound(btn_modebasic);
        SoundEffects.addSound(btn_back);
    

        btn_modebasic.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    SoundEffects.clickSound.play();

                    AnchorPane settings = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/ThemeChooser.fxml"));
                    settings.setLayoutX((root.getWidth() - settings.getPrefWidth()) / 2);
                    settings.setLayoutY((root.getHeight() - settings.getPrefHeight()) / 2);
                    root.getChildren().add(settings);
                    settings.requestFocus();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                SoundEffects.clickSound.play();
                DbController.changeScene(arg0, "Loggedin.fxml");
            }
        });


    }



}
