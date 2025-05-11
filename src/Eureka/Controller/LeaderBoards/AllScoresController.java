package Eureka.Controller.LeaderBoards;

import Eureka.Controller.ui.SceneManager;
import Eureka.models.SoundEffects;
import Eureka.models.LeaderBoardRep.ScoreEntry;
import Eureka.models.PlayerRep.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AllScoresController {

    @FXML
    private TableView<ScoreEntry> tableScores;
    @FXML
    private TableColumn<ScoreEntry, String> colMode;
    @FXML
    private TableColumn<ScoreEntry, Integer> colScore;

    @FXML
    private Button btn_back;


    @FXML
    public void initialize() {
        SoundEffects.addSound(btn_back);
        Player player = Player.getCurrentPlayer();

        colMode.setCellValueFactory(new PropertyValueFactory<>("mode"));
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        ObservableList<ScoreEntry> data = FXCollections.observableArrayList(
            new ScoreEntry("Basic",   player.getBestScore()),
            new ScoreEntry("Survival",       player.getBestSurvivalScore()),
            new ScoreEntry("TimeTrial",      player.getBestTimeTrialScore()),
            new ScoreEntry("Progressive Time Trial",    player.getBestProgressiveTimeTrialScore()),
            new ScoreEntry("Mcq", player.getBestMcqScore()),
            new ScoreEntry("Missing Letters", player.getBestMissingLetterScore())
            
        );

        tableScores.setItems(data);

        // Bouton retour
        btn_back.setOnAction(e -> {
            SceneManager.changeScene(e, "Profile.fxml");
        });
    }
}