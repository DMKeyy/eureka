package Eureka.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ChoseGameModeController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btn_modebasic;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_modesurvival;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SoundEffects.addSound(btn_modebasic);
        SoundEffects.addSound(btn_back);
        SoundEffects.addSound(btn_modesurvival);

        btn_modebasic.setOnAction(event -> {
            try {
                SoundEffects.clickSound.play();
                AnchorPane settings = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/ThemeChooser.fxml"));
                settings.setLayoutX((root.getWidth() - settings.getPrefWidth()) / 2);
                settings.setLayoutY((root.getHeight() - settings.getPrefHeight()) / 2);
                root.getChildren().add(settings);
                settings.requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_modesurvival.setOnAction(event -> {
            try {
                SoundEffects.clickSound.play();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Eureka/View/fxml/ThemeChooser.fxml"));
                AnchorPane settings = loader.load();
        
                ThemeChooserController controller = loader.getController();
                controller.setSurvivalMode(true); // 👈 Flag important
        
                settings.setLayoutX((root.getWidth() - settings.getPrefWidth()) / 2);
                settings.setLayoutY((root.getHeight() - settings.getPrefHeight()) / 2);
                root.getChildren().add(settings);
                settings.requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
    

        btn_back.setOnAction(event -> {
            SoundEffects.clickSound.play();
            DbController.changeScene(event, "Loggedin.fxml");
        });
    }

}
