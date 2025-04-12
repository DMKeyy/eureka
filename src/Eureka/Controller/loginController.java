package Eureka.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Eureka.models.SoundEffects;
import javafx.animation.FadeTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class loginController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private Button btn_login;

    @FXML 
    public Button btn_signup, btn_toforgotpassword;

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
        

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        TranslateTransition floatText = new TranslateTransition(Duration.seconds(2), btn_login);
        floatText.setByY(-10);
        floatText.setCycleCount(TranslateTransition.INDEFINITE);
        floatText.setAutoReverse(true);
        floatText.play();

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
                DbController.loginUser(event, tf_username.getText(), pf_password.getText());
                loginController.animateButtonClick(btn_login);
            }
        });


        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               DbController.changeScene(event, "SignUp.fxml");
            }
        });

        btn_toforgotpassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbController.changeScene(event, "ForgotPassword.fxml");
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