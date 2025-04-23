package Eureka.Controller;

import Eureka.models.SoundEffects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;


public class AboutController {

    @FXML
    private Button btn_back;


    @FXML
    public void Backtomain(ActionEvent event) {
        SoundEffects.addSound(btn_back);
        SceneManager.changeScene(event, "LoggedIn.fxml");
    }

   

}
