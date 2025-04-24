package Eureka.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class AboutController {

    @FXML
    private Button btn_back;


    @FXML
    public void Backtomain(ActionEvent event) {
        SoundEffects.clickSound.play();
        DbController.changeScene(event, "LoggedIn.fxml");
    }

   

}
