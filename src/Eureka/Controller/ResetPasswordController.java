package Eureka.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ResetPasswordController implements Initializable {

    
    @FXML
    private Text txt_result;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField pf_password, pf_confirmpassword;
    @FXML
    private Button btn_resetpassword, btn_tologin;
    @FXML
    private AnchorPane root;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

         FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        TranslateTransition floatText = new TranslateTransition(Duration.seconds(2), btn_resetpassword);
        floatText.setByY(-10);
        floatText.setCycleCount(TranslateTransition.INDEFINITE);
        floatText.setAutoReverse(true);
        floatText.play();

        btn_resetpassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = tf_username.getText();
                String password = pf_password.getText();
                String confirmPassword = pf_confirmpassword.getText();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    shake(btn_resetpassword);
                    shake(tf_username);
                    shake(pf_password);
                    shake(pf_confirmpassword);
                    pf_password.clear();
                    pf_confirmpassword.clear();
                    tf_username.clear();    
                    btn_tologin.setVisible(true);
                    txt_result.setText("Please fill in all fields.");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    shake(btn_resetpassword);
                    shake(tf_username);
                    shake(pf_password);
                    shake(pf_confirmpassword);
                    pf_password.clear();
                    pf_confirmpassword.clear();
                    tf_username.clear();
                    btn_tologin.setVisible(true);
                    txt_result.setText("Passwords do not match.");
                    return;
                }

                if (!DbController.isUsernameValid(username)) {
                    shake(btn_resetpassword);
                    shake(tf_username);
                    shake(pf_password);
                    shake(pf_confirmpassword);
                    pf_password.clear();
                    pf_confirmpassword.clear();
                    tf_username.clear();
                    txt_result.setText("Username not found!");
                    btn_tologin.setVisible(true);
                    return;
                }

                if (DbController.updatePassword(username, password)) {
                    txt_result.setText("Password reset successfully!");
                    btn_tologin.setVisible(true);
                    btn_resetpassword.setVisible(false);
                } else {
                    shake(btn_resetpassword);
                    shake(tf_username);
                    shake(pf_password);
                    shake(pf_confirmpassword);
                    txt_result.setText("Error resetting password. Try again.");
                }
            }
        });

        btn_tologin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbController.changeScene(event, "LogIn.fxml");
            }
        });
    }

    public static void shake(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50), node);
        tt.setFromX(0);
        tt.setByX(10);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }

}
