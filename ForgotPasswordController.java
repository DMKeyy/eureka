package Eureka.Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

public class ForgotPasswordController implements Initializable {

    @FXML private TextField tf_username;
    @FXML private PasswordField tf_newPassword, tf_confirmPassword;
    @FXML private Button btn_reset, btn_backToLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_reset.setOnAction(event -> {
            animateButton(btn_reset);
            handleReset(event);
        });

        btn_backToLogin.setOnAction(event ->
            DbController.changeScene(event, "LogIn.fxml"));
    }

    private void handleReset(ActionEvent event) {
        String username = tf_username.getText();
        String newPass = tf_newPassword.getText();
        String confirmPass = tf_confirmPassword.getText();

        if (username.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            alert(Alert.AlertType.ERROR, "All fields must be filled.");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            alert(Alert.AlertType.ERROR, "Passwords don't match.");
            return;
        }

        if (updatePassword(username, newPass)) {
            alert(Alert.AlertType.INFORMATION, "Password reset successfully!");
            DbController.changeScene(event, "LogIn.fxml");
        } else {
            alert(Alert.AlertType.ERROR, "Username not found.");
        }
    }

    private boolean updatePassword(String username, String password) {
        try (Connection conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/bdd", "root", "")) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE player SET Password = ? WHERE Username = ?");
            stmt.setString(1, password);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void animateButton(Button btn) {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), btn);
        st.setToX(0.95);
        st.setToY(0.95);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    private void alert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.showAndWait();
    }
}



