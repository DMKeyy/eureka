package Eureka.Controller;

import java.io.IOException;


import Eureka.models.SoundEffects;
import Eureka.models.BadgeRep.BadgeRepository;
import Eureka.models.PlayerRep.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class ProfileController {

    @FXML
    private AnchorPane root;

    @FXML
    private Label lbl_username_value;

    @FXML
    private Label lbl_password_value;

    @FXML
    private Label lbl_date_value;
    @FXML
    private Label lbl_bestscore_value;
    @FXML
    private Label lbl_totalGames_value;

    @FXML
    private Label lbl_badgeCount;
    @FXML
    private Button btn_back;
    @FXML
    private Button btn_change_password;
    @FXML
    private Button btn_change_username;
    // TON Hyperlink
    @FXML
    private Hyperlink BestScore;

    @FXML
    private Hyperlink Badges;

    @FXML
    public void initialize() {
        SoundEffects.addSound(btn_back);
        SoundEffects.addSound(btn_change_password);
        SoundEffects.addSound(btn_change_username);

        Player player = Player.getCurrentPlayer();
        if (player != null) {
            lbl_username_value.setText(player.getUsername());
            lbl_password_value.setText(player.getPassword());
            lbl_date_value.setText(String.valueOf(player.getRegistrationDate()));
            lbl_bestscore_value.setText(String.valueOf(player.getBestScore()));
            lbl_totalGames_value.setText(String.valueOf(player.getTotalGamesPlayed()));
            lbl_badgeCount.setText(BadgeRepository.getPlayerBadges(player.getPlayerId()).size() + "");

        }

        // Bouton "Back"
        btn_back.setOnAction(e -> {
            SceneManager.changeScene(e, "ChoseGameMode.fxml");
        });

        BestScore.setOnAction(_ -> {
            SoundEffects.clickSound.play();
            showOverlay("/Eureka/View/fxml/AllScores.fxml");
        });
        
        Badges.setOnAction(_ -> {
            SoundEffects.clickSound.play();
            showOverlay("/Eureka/View/fxml/Badges.fxml");
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
    
    

   
 public void updatePasswordLabel() {
    lbl_password_value.setText(Player.getCurrentPlayer().getPassword());
}
public void updateUsernameLabel() {
    lbl_username_value.setText(Player.getCurrentPlayer().getUsername());
}



}