package Eureka.Controller.ui;

import java.io.IOException;

import Eureka.Controller.auth.PasswordController;
import Eureka.Controller.auth.UsernameController;
import Eureka.models.SoundEffects;
import Eureka.models.PlayerRep.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class ProfileController {

    @FXML
    private AnchorPane root;

    @FXML
    private Label lbl_username_value;

    @FXML
    private Label lbl_date_value;
    @FXML
    private Label lbl_totalGames_value;

    @FXML
    private Button btn_back;
    @FXML
    private Button btn_change_password;
    @FXML
    private Button btn_change_username;
    @FXML
    private Button BestScore;

    @FXML
    private Button Badges;

    @FXML
    public void initialize() {
        SoundEffects.addSound(btn_back);
        SoundEffects.addSound(btn_change_password);
        SoundEffects.addSound(btn_change_username);

        Player player = Player.getCurrentPlayer();
        if (player != null) {
            lbl_username_value.setText(player.getUsername());
            lbl_date_value.setText(String.valueOf(player.getRegistrationDate()));
            lbl_totalGames_value.setText(String.valueOf(player.getTotalGamesPlayed()));

        }

        
        btn_back.setOnAction(e -> {
            SoundEffects.clickSound.play();
            SceneManager.changeScene(e, "LoggedIn.fxml");
        });

        BestScore.setOnAction(e -> {
            SoundEffects.clickSound.play();
            SceneManager.showPopup(root, "Allscores.fxml");
        });
        
        Badges.setOnAction(e-> {
            SoundEffects.clickSound.play();
            SceneManager.showPopup(root, "Badges.fxml");
        });
        
        btn_change_password.setOnAction(_ -> {
            showOverlay("/Eureka/View/fxml/Password.fxml");
        });
        
        btn_change_username.setOnAction(_ -> {
            showOverlay("/Eureka/View/fxml/Username.fxml");
        });

    }

    private void showOverlay(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane overlay = loader.load();
    
            Object controller = loader.getController();
            if (controller instanceof PasswordController) {
                ((PasswordController) controller).setProfileController(this);
            } else if (controller instanceof UsernameController) {
                ((UsernameController) controller).setProfileController(this);
            }
            overlay.setPrefWidth(overlay.getPrefWidth());
            overlay.setPrefHeight(overlay.getPrefHeight());
    
            // Centre l'overlay
            double x = (root.getWidth() - overlay.getPrefWidth()) / 2;
            double y = (root.getHeight() - overlay.getPrefHeight()) / 2;
    
            overlay.setLayoutX(x);
            overlay.setLayoutY(y);
    
            // Évite que le panneau se redimensionne automatiquement
            AnchorPane.setTopAnchor(overlay, null);
            AnchorPane.setBottomAnchor(overlay, null);
            AnchorPane.setLeftAnchor(overlay, null);
            AnchorPane.setRightAnchor(overlay, null);
    
            root.getChildren().add(overlay);
            overlay.requestFocus();
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
public void updateUsernameLabel() {
    lbl_username_value.setText(Player.getCurrentPlayer().getUsername());
}
}