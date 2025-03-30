package Eureka.Controller;

import Eureka.models.GameData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ThemeChooserController {
@FXML 
    private RadioButton ThemeScience,ThemeHistory,ThemeJava,ThemeGeography,ThemeArt,ThemeSport;

    @FXML 
    private RadioButton diff_easy,diff_medium,diff_hard;

    @FXML
    private ToggleGroup Themegroupe,Difficultygroupe;

    @FXML
    private Button btn_back,btn_start;

    String theme;
    String difficulty;
    private boolean isSurvivalMode = false;

    public void setSurvivalMode(boolean survivalMode) {
    this.isSurvivalMode = survivalMode;  
    }


    public void initialize(){
        SoundEffects.addSound(btn_back);
        SoundEffects.addSound(btn_start);

        btn_back.setOnAction(e -> {
            SoundEffects.clickSound.play();
           DbController.changeScene(e, "ChoseGameMode.fxml");
        });

        btn_start.setOnAction(e -> {
            SoundEffects.clickSound.play();

            if (Themegroupe.getSelectedToggle() == null) {
                DbController.showAlert(AlertType.ERROR,"Please select a theme before proceeding!");
                return;
            }
    
            if (Difficultygroupe.getSelectedToggle() == null) {
                DbController.showAlert(AlertType.ERROR,"Please select a difficulty before proceeding!");
                return;
            }
    
            theme = ((RadioButton) Themegroupe.getSelectedToggle()).getText();
            GameData.setTheme(theme);

            difficulty = ((RadioButton) Difficultygroupe.getSelectedToggle()).getText();
            int dif;
            switch (difficulty) {
                case "Easy":
                    dif =1;
                    break;
                case "Medium":
                    dif=2;
                    break;
                case "Hard":
                    dif=3;
                    break;
                default:
                    dif=1;
                    break;
            }
            GameData.setDifficulty(dif);
            
            if (isSurvivalMode) {
                DbController.changeScene(e, "SurvivalGameMode.fxml");
            } else {
                DbController.changeScene(e, "BasicGameMode.fxml");
            }
            
        });


    }

}
