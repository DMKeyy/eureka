package Eureka.Controller.ui;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    public static AnchorPane showPopup(AnchorPane root, String fxmlPath) {
        try {
            AnchorPane popup = FXMLLoader.load(SceneManager.class.getResource("/Eureka/View/fxml/" + fxmlPath));
            
            // Center the popup
            popup.setLayoutX((root.getWidth() - popup.getPrefWidth()) / 2);
            popup.setLayoutY((root.getHeight() - popup.getPrefHeight()) / 2);

            // Initial scale
            popup.setScaleX(0);
            popup.setScaleY(0);

            // Add to parent
            root.getChildren().add(popup);
            popup.requestFocus();

            // Animate
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), popup);
            scaleTransition.setFromX(0);
            scaleTransition.setFromY(0);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();

            return popup;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}