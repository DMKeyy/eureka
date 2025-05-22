package Eureka.Controller.auth;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;

import Eureka.models.SoundEffects;
import Eureka.models.PlayerRep.PlayerRepository;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;
import Eureka.Controller.ui.SceneManager;

public class SignUpController implements Initializable {

    @FXML
    private Button btn_login;

    @FXML 
    private Button btn_signup;

    @FXML
    private TextField tf_username;

    @FXML 
    private TextField tf_password,tf_password1;

    @FXML
    private PasswordField pf_password,pf_password1;

    @FXML
    private Button btn_leave;

    @FXML
    private CheckBox showPasswordCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SoundEffects.addSound(btn_login);
        SoundEffects.addSound(btn_signup);

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {            @Override
            public void handle(ActionEvent event) {
                String username = tf_username.getText().trim();
                String password = pf_password.getText().trim();
                String confirmPassword = pf_password1.getText().trim();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    SceneManager.showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    SceneManager.showAlert(Alert.AlertType.ERROR, "Passwords do not match");
                    return;
                }

                try {
                    if (PlayerRepository.isUsernameValid(username)) {
                        PlayerRepository.signUpUser(username, password);
                        SceneManager.changeScene(event, "LoggedIn.fxml");
                    } else {
                        SceneManager.showAlert(Alert.AlertType.ERROR, "Username already exists");
                    }
                } catch (SQLException e) {
                    SceneManager.showAlert(AlertType.ERROR, e.getMessage());
                }
            }
        });


        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneManager.changeScene(event, "LogIn.fxml");
            }
        });

        btn_leave.setOnAction(_ -> {
            PauseTransition pause = new PauseTransition(Duration.millis(300));
            pause.setOnFinished(_ -> System.exit(0));
            pause.play();
        });        // Bind password fields with their text field counterparts
        tf_password.textProperty().bindBidirectional(pf_password.textProperty());
        tf_password1.textProperty().bindBidirectional(pf_password1.textProperty());

        showPasswordCheckBox.selectedProperty().addListener((_, _, newVal) -> {
            if (newVal) {
                // Show text fields
                pf_password.setVisible(false);
                pf_password.setManaged(false);
                pf_password1.setVisible(false);
                pf_password1.setManaged(false);

                tf_password.setVisible(true);
                tf_password.setManaged(true);
                tf_password1.setVisible(true);
                tf_password1.setManaged(true);
            } else {
                // Show password fields
                pf_password.setVisible(true);
                pf_password.setManaged(true);
                pf_password1.setVisible(true);
                pf_password1.setManaged(true);

                tf_password.setVisible(false);
                tf_password.setManaged(false);
                tf_password1.setVisible(false);
                tf_password1.setManaged(false);
            }
        });
    }
}
