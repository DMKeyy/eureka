package Eureka.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SceneManager {
    
    public static void changeScene(ActionEvent event, String fxml) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource("/Eureka/View/fxml/" + fxml));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Platform.runLater(stage::centerOnScreen);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}