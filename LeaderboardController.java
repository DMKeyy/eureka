package Eureka.Controller;

import Eureka.models.LeaderBoardRep.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;


public class LeaderboardController {

    private LeaderboardRepository leaderboardRepository = new LeaderboardRepository();


    @FXML
    private TableView<LeaderboardEntry> basicModeTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> basicModeUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> basicModeScoreColumn;

    @FXML
    private TableView<LeaderboardEntry> survivalModeTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> survivalModeUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> survivalModeScoreColumn;

    @FXML
    private TableView<LeaderboardEntry> timeTrialTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> timeTrialUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> timeTrialScoreColumn;

    @FXML
    private TableView<LeaderboardEntry> progressiveTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> progressiveUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> progressiveScoreColumn;

    @FXML
    private TableView<LeaderboardEntry> missingLetterTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> missingLetterUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> missingLetterScoreColumn;

    @FXML
    private TableView<LeaderboardEntry> mcqTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> mcqUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> mcqScoreColumn;

    @FXML
    private TableView<LeaderboardEntry> scienceTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> scienceUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> scienceAnswersColumn;

    @FXML
    private TableView<LeaderboardEntry> historyTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> historyUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> historyAnswersColumn;

    @FXML
    private TableView<LeaderboardEntry> geographyTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> geographyUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> geographyAnswersColumn;

    @FXML
    private TableView<LeaderboardEntry> sportTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> sportUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> sportAnswersColumn;

    @FXML
    private TableView<LeaderboardEntry> artTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> artUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> artAnswersColumn;

    @FXML
    private TableView<LeaderboardEntry> javaTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> javaUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> javaAnswersColumn;

    @FXML
    private TableView<LeaderboardEntry> islamTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> islamUsernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> islamAnswersColumn;

    @FXML
    private Button btn_back;

    @FXML
    private void initialize() {
        System.out.println("Initializing LeaderboardController...");
        // Remplir tous les tableaux au lancement !
        setupScoreTable(basicModeTable, basicModeUsernameColumn, basicModeScoreColumn, leaderboardRepository.getBasicLeaderboard());
        setupScoreTable(survivalModeTable, survivalModeUsernameColumn, survivalModeScoreColumn, leaderboardRepository.getSurvivalLeaderboard());
        setupScoreTable(timeTrialTable, timeTrialUsernameColumn, timeTrialScoreColumn, leaderboardRepository.getTimeTrialLeaderboard());
        setupScoreTable(progressiveTable, progressiveUsernameColumn, progressiveScoreColumn, leaderboardRepository. getProgressiveTimeTrialLeaderboard());
        setupScoreTable(missingLetterTable, missingLetterUsernameColumn, missingLetterScoreColumn, leaderboardRepository.getMissingLetterLeaderboard());
        setupScoreTable(mcqTable, mcqUsernameColumn, mcqScoreColumn, leaderboardRepository.getMcqLeaderboard());

        setupCorrectAnswersTable(scienceTable, scienceUsernameColumn, scienceAnswersColumn, leaderboardRepository.getScienceLeaderboard());
        setupCorrectAnswersTable(historyTable, historyUsernameColumn, historyAnswersColumn, leaderboardRepository.getHistoryLeaderboard());
        setupCorrectAnswersTable(geographyTable, geographyUsernameColumn, geographyAnswersColumn, leaderboardRepository.getGeographyLeaderboard());
        setupCorrectAnswersTable(sportTable, sportUsernameColumn, sportAnswersColumn, leaderboardRepository.getSportLeaderboard());
        setupCorrectAnswersTable(artTable, artUsernameColumn, artAnswersColumn, leaderboardRepository.getArtLeaderboard());
        setupCorrectAnswersTable(javaTable, javaUsernameColumn, javaAnswersColumn, leaderboardRepository.getJavaLeaderboard());
        setupCorrectAnswersTable(islamTable, islamUsernameColumn, islamAnswersColumn, leaderboardRepository.getIslamLeaderboard());
    }

    private void setupScoreTable(TableView<LeaderboardEntry> table, TableColumn<LeaderboardEntry, String> usernameColumn,
    TableColumn<LeaderboardEntry, Integer> scoreColumn, List<LeaderboardEntry> entries) {
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
     table.setItems(FXCollections.observableArrayList(entries));
     }
     private void setupCorrectAnswersTable(TableView<LeaderboardEntry> table, TableColumn<LeaderboardEntry, String> usernameColumn,
     TableColumn<LeaderboardEntry, Integer> correctAnswersColumn, List<LeaderboardEntry> entries) {
     usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
     correctAnswersColumn.setCellValueFactory(new PropertyValueFactory<>("correctAnswers"));
     table.setItems(FXCollections.observableArrayList(entries));
    }
    @FXML
    private void handleBackButton(ActionEvent event) {
        System.out.println("Back button clicked");
       SceneManager.changeScene(event, "Loggedin.fxml");
    }

   }
