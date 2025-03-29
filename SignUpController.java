package Eureka.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class SignUpController implements Initializable {

    @FXML
    private Button btn_signup;

    @FXML
    private Button btn_login; // Pour revenir à l'écran de connexion

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    
    @FXML
    private TextField tf_password_text;

    @FXML
    private CheckBox showPasswordCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        if (tf_password_text != null) {
            
            tf_password_text.textProperty().bindBidirectional(tf_password.textProperty());
            tf_password_text.setVisible(false);
        }

       
        showPasswordCheckBox.setOnAction(event -> {
            if (showPasswordCheckBox.isSelected()) {
                tf_password_text.setVisible(true);
                tf_password.setVisible(false);
            } else {
                tf_password_text.setVisible(false);
                tf_password.setVisible(true);
            }
        });

        
        btn_signup.setOnAction((ActionEvent event) -> {
            String username = tf_username.getText().trim();
            String password = tf_password.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
            } else {
                DbController.signUpUser(event, username, password);
                animateButtonClick(btn_signup);
            }
        });

   
        btn_login.setOnAction((ActionEvent event) -> {
            DbController.changeScene(event, "LogIn.fxml");
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

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Sign Up");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

