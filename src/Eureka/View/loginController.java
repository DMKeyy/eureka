package Eureka.View;

import java.net.URL;
import java.util.ResourceBundle;

import Eureka.Controller.DbController;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class loginController implements Initializable {
    @FXML
    private Button btn_login;

    @FXML 
    private Button btn_signup;

    @FXML
    private TextField tf_username;

    @FXML 
    private TextField tf_password;

    public void initialize(URL location, ResourceBundle resources) {
        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbController.loginUser(event, tf_username.getText(), tf_password.getText());
                loginController.animateButtonClick(btn_login);
            }
        });


        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               DbController.changeScene(event, "SignUp.fxml");
            }
        });

         
    }

    public static void animateButtonClick(Button button) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(100), button);
        scale.setToX(0.95);
        scale.setToY(0.95);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

}
