package Eureka.View;

import java.net.URL;
import java.util.ResourceBundle;

import Eureka.Controller.DbController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class LoggedInController implements Initializable {

    @FXML
    private Button btn_logout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbController.changeScene(event, "LogIn.fxml");
            }
        });
    }
}
