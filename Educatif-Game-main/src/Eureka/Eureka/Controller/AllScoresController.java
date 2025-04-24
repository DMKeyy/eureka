package Eureka.Controller;

import Eureka.models.Player;
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

    // Classe interne représentant un score par mode
    public static class ScoreEntry {
        private String mode;
        private int score;

        public ScoreEntry(String mode, int score) {
            this.mode = mode;
            this.score = score;
        }
        public String getMode() { return mode; }
        public int getScore() { return score; }
    }

    @FXML
    public void initialize() {
        Player player = Player.getCurrentPlayer();

        // Configurer la table
        colMode.setCellValueFactory(new PropertyValueFactory<>("mode"));
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Créer la liste des scores par mode
        ObservableList<ScoreEntry> data = FXCollections.observableArrayList(
            new ScoreEntry("Basic/Normal",   player.getBestScore()),          // bestScore
            new ScoreEntry("Survival",       player.getBestSurvivalScore()), // bestSurvivalScore
            new ScoreEntry("TimeTrial",      player.getBestTimeTrialScore()) // bestTimeTrialScore
            // tu peux ajouter d'autres modes si tu en as
        );

        tableScores.setItems(data);

        // Bouton retour
        btn_back.setOnAction(e -> {
            SoundEffects.clickSound.play();
            DbController.changeScene(e, "Profile.fxml");
        });
    }
}
