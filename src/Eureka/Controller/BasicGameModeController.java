package Eureka.Controller;

import java.io.IOException;
import java.util.List;

import Eureka.models.GameData;
import Eureka.models.Player;
import Eureka.models.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class BasicGameModeController {
    String theme;
    int difficulty;
    int score;
    int correctAnswers;
    PenduDrawer pendu;
    Question question;
    int streakCount = 0;
    int longestStreak = 0;

    @FXML
    private AnchorPane root;

    @FXML
    private Label questionLabel;
    @FXML 
    private TextField tf_answer;
    @FXML
    private Label scoreText;
    @FXML
    private Button btn_submit;
    @FXML 
    private ImageView headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage ,leftfeetImage, rightfeetImage;
    
    public BasicGameModeController() {
        this.score = 0;
        this.correctAnswers = 0;
    }


    @FXML
    public void initialize() {

        this.theme = GameData.getTheme();
        this.difficulty = GameData.getDifficulty();
        pendu = new PenduDrawer(List.of(headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage, leftfeetImage, rightfeetImage), 8);


        LoadNextQuestion();
        btn_submit.setOnAction(event -> handleSubmit(event));
    }


    public void UpdateCurrentPlayer(String Theme) {
        Player player = Player.getCurrentPlayer();
        if (score > player.getBestScore()) {
            player.setBestScore(score);
        }

        if (longestStreak > player.getStreakCount()) { // Store longest streak
            player.setStreakCount(longestStreak);
        }

        switch (Theme) {
            case "Science":
                player.setCorrectAnswersScience(player.getCorrectAnswersScience()+correctAnswers);
                break;
            case "History":
                player.setCorrectAnswersHistory(player.getCorrectAnswersHistory()+correctAnswers);
                break;
            case "Geography":
                player.setCorrectAnswersGeography(player.getCorrectAnswersGeography()+correctAnswers);
                break;
            case "Sport":
                player.setCorrectAnswersSport(player.getCorrectAnswersSport()+correctAnswers);
                break;
            case "Art":
                player.setCorrectAnswersArt(player.getCorrectAnswersArt()+correctAnswers);
                break;
            case "Java":
                player.setCorrectAnswersJava(player.getCorrectAnswersJava()+correctAnswers);
                break;
            case "Islam":
                player.setCorrectAnswersIslam(player.getCorrectAnswersIslam()+correctAnswers);
                break;  
            default:
                break;
        }

        player.setTotalGamesPlayed(player.getTotalGamesPlayed() + 1);

    }

    public void LoadNextQuestion() {
        question = DbController.getQuestion(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
        scoreText.setText("Score: " + score);
    }

    public void handleSubmit(ActionEvent e) {
        if (question == null) return;
        if (tf_answer.getText().isEmpty()) return;

        if (question.checkAnswer(tf_answer.getText())) {
            score++;
            correctAnswers++;
            streakCount++;
            if (streakCount > longestStreak) {
                longestStreak = streakCount;
            }

            scoreText.setText("Score: " + score);
        } else {
            pendu.setAttemptsLeft(pendu.getAttemptsLeft() - 1);
            pendu.drawNextPart();
            streakCount = 0;

            if (pendu.isGameOver()) {
                endgame();
                return;
            }
        }

        tf_answer.clear();
        LoadNextQuestion();
    }

    public void endgame() {
        UpdateCurrentPlayer(theme);
        DbController.updatePlayer();
        DbController.resetUsedQuestions();

        try {
            SoundEffects.clickSound.play();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Eureka/View/fxml/GameOver.fxml"));
            AnchorPane gameover = loader.load();

            GameOverController gameOverController = loader.getController();
            gameOverController.setScore(score);
  
            gameover.setLayoutX((root.getWidth() - gameover.getPrefWidth()) / 2);
            gameover.setLayoutY((root.getHeight() - gameover.getPrefHeight()) / 2);
            root.getChildren().add(gameover);
            gameover.requestFocus();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
