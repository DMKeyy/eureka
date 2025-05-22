package Eureka.Controller.auth;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;

import Eureka.models.PlayerRep.Player;
import Eureka.models.PlayerRep.PlayerRepository;
import Eureka.Controller.ui.SceneManager;
import Eureka.models.SoundEffects;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class loginController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private Button btn_login;

    @FXML 
    public Button btn_signup, btn_toforgotpassword,btn_leave;

    @FXML
    private TextField tf_username, tf_password;

    @FXML 
    private PasswordField pf_password;

    @FXML
    private CheckBox showPasswordCheckBox;
    
    
    public void initialize(URL location, ResourceBundle resources) {

        SoundEffects.addSound(btn_login);
        SoundEffects.addSound(btn_signup);
        SoundEffects.addSound(btn_toforgotpassword);

        tf_password.textProperty().bindBidirectional(pf_password.textProperty());

        showPasswordCheckBox.selectedProperty().addListener((_, _, newVal) -> {
            if (newVal) {
                // Afficher TextField
                pf_password.setVisible(false);
                pf_password.setManaged(false);

                tf_password.setVisible(true);
                tf_password.setManaged(true);
            } else {
                // Revenir au PasswordField
                pf_password.setVisible(true);
                pf_password.setManaged(true);

                tf_password.setVisible(false);
                tf_password.setManaged(false);
            }
        });

        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Player player = PlayerRepository.loginUser(tf_username.getText(), pf_password.getText());
                    Player.setCurrentPlayer(player);
                    SceneManager.changeScene(event, "LoggedIn.fxml");
                } catch (SQLException e) {
                    SceneManager.showAlert(AlertType.ERROR, e.getMessage());
                }
            }
        });

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               SceneManager.changeScene(event, "SignUp.fxml");
            }
        });

        btn_toforgotpassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneManager.changeScene(event, "ForgotPassword.fxml");
            }
        });

        btn_leave.setOnAction(_ -> {
            PauseTransition pause = new PauseTransition(Duration.millis(300));
            pause.setOnFinished(_ -> System.exit(0));
            pause.play();
        });
   
    }

}