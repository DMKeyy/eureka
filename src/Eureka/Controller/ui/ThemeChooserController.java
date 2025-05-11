package Eureka.Controller.ui;

import Eureka.models.GameData;
import Eureka.models.SoundEffects;
import Eureka.models.GameModeRep.GameMode;
import Eureka.models.ThemeRep.ThemeRepository;
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

    public void initialize(){
        SoundEffects.addSound(btn_back);
        SoundEffects.addSound(btn_start);

        btn_back.setOnAction(e -> {
           SceneManager.changeScene(e, "ChoseGameMode.fxml");
        });

        btn_start.setOnAction(e -> {

            if (Themegroupe.getSelectedToggle() == null) {
                SceneManager.showAlert(AlertType.ERROR,"Please select a theme before proceeding!");
                return;
            }
    
            if (Difficultygroupe.getSelectedToggle() == null) {
                SceneManager.showAlert(AlertType.ERROR,"Please select a difficulty before proceeding!");
                return;
            }
    
            theme = ((RadioButton) Themegroupe.getSelectedToggle()).getText();
            GameData.setTheme(ThemeRepository.getThemeByName(theme));

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

            
            GameMode mode = GameData.getMode();

                switch (mode.getName()) {
                    case "Basic":
                    SceneManager.changeScene(e, "BasicGameMode.fxml");
                    break;
                    case "Survival":
                    SceneManager.changeScene(e, "SurvivalGameMode.fxml");
                    break;
                    case "Multi":
                    SceneManager.changeScene(e, "LocalMultiplayer.fxml");
                    break;
                    case "TimeTrial":
                    SceneManager.changeScene(e, "TimeTrialGameMode.fxml");
                    break;
                    case "ProgressiveTimeTrial":
                    SceneManager.changeScene(e, "ProgressiveTimeTrialGameMode.fxml");
                    break;
                    case "MissingLetters":
                    SceneManager.changeScene(e, "MissingLetterGameMode.fxml");
                    break;
                    case "Mcq":
                    SceneManager.changeScene(e, "McqGameMode.fxml");
                    break;
                    default:
                    SceneManager.showAlert(AlertType.ERROR,"Invalid game mode selected.");
                    break;
            }
            
        });

    }

}
