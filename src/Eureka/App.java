package Eureka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/LogIn.fxml"));
            Scene scene = new Scene(root);

            stage.setScene(scene);
            
            stage.setTitle("Eureka");
            stage.show();
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}