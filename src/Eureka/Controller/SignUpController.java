package Eureka.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;

import Eureka.models.*;
import Eureka.models.PlayerRep.PlayerRepository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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
        SoundEffects.addSound(btn_login);
        SoundEffects.addSound(btn_signup);

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                    try {
                        PlayerRepository.signUpUser(tf_username.getText(), tf_password.getText());
                        SceneManager.changeScene(event, "LoggedIn.fxml");
                    } catch (SQLException e) {
                        SceneManager.showAlert(AlertType.ERROR, e.getMessage());
                    }
                } else {
                    System.out.println("Please fill all the fields");
                    SceneManager.showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
                }
                loginController.animateButtonClick(btn_signup);
            }
        });

        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneManager.changeScene(event, "LogIn.fxml");
            }
        });
    }
}
