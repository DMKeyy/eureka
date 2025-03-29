package Eureka.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class loginController implements Initializable {

    @FXML
    private Button btn_login;

    @FXML
    private Button btn_signup;

    @FXML
    private Button btn_toforgotpassword;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    // Champ pour afficher le mot de passe en clair
    @FXML
    private TextField tf_password_text;

    @FXML
    private CheckBox showPasswordCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Synchroniser le contenu des deux champs pour le mot de passe
        if (tf_password_text != null) {
            tf_password_text.textProperty().bindBidirectional(tf_password.textProperty());
            tf_password_text.setVisible(false);
        }

        // Gérer l'affichage du mot de passe en fonction de la CheckBox
        showPasswordCheckBox.setOnAction(event -> {
            if (showPasswordCheckBox.isSelected() && tf_password_text != null) {
                tf_password_text.setVisible(true);
                tf_password.setVisible(false);
            } else {
                if (tf_password_text != null) {
                    tf_password_text.setVisible(false);
                }
                tf_password.setVisible(true);
            }
        });

        // Bouton login : action de connexion
        btn_login.setOnAction(event -> {
            DbController.loginUser(event, tf_username.getText(), tf_password.getText());
            animateButtonClick(btn_login);
        });

        // Bouton sign up : redirection vers la page d'inscription
        btn_signup.setOnAction(event -> {
            DbController.changeScene(event, "SignUp.fxml");
            animateButtonClick(btn_signup);
        });

        // Bouton forgot password : redirection vers l'écran ForgotPassword
        btn_toforgotpassword.setOnAction(event -> {
            DbController.changeScene(event, "ForgotPassword.fxml");
        });
    }

    // Animation d'un bouton lors du clic (scale down puis up)
    public static void animateButtonClick(Button button) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(100), button);
        scale.setToX(0.95);
        scale.setToY(0.95);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }
}
