package Eureka.View;

import java.net.URL;
import java.util.ResourceBundle;

import Eureka.Controller.DbController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUpController implements Initializable {

    @FXML
    private Button btn_login;

    @FXML 
    private Button btn_signup;

    @FXML
    private TextField tf_username;

    @FXML 
    private TextField tf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                    DbController.signUpUser(event, tf_username.getText(), tf_password.getText());
                    
                }
                else {
                    System.out.println("Please fill all the fields");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all the fields");
                    alert.show();
                }
                
            }
        });

        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbController.changeScene(event, "LogIn.fxml");
            }
        });
       
    }

}
