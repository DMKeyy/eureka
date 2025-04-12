package Eureka.Controller;

import Eureka.models.GameData;
import Eureka.models.PenduDrawer;
import Eureka.models.Player;
import Eureka.models.Question;
import Eureka.models.SoundEffects;
import Eureka.models.WrongAnswerStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class SurvivalGameModeController {

    @FXML
    private AnchorPane root;

    @FXML
    private Label questionLabel;

    @FXML
    private TextField tf_answer;

    @FXML
    private Label scoreLabel;

    @FXML
    private Button btn_submit;

    @FXML
    private ImageView headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage, leftfeetImage, rightfeetImage;

    private PenduDrawer pendu;
    private Question question;
    private int score = 0;
    private int correctAnswers = 0;
    int streakCount = 0;
    int longestStreak = 0;
    private String theme;
    private int difficulty;

    @FXML
    public void initialize() {
        theme = GameData.getTheme();
        difficulty = GameData.getDifficulty();

        pendu = new PenduDrawer(List.of(
                headImage, bodyImage, leftArmImage, rightArmImage,
                leftLegImage, rightLegImage, leftfeetImage, rightfeetImage
        ), 8);
        pendu.reset();

        loadNextQuestion();

        btn_submit.setOnAction(this::handleSubmit);
    }

    public void loadNextQuestion() {
        question = DbController.getQuestion(theme, difficulty);
        if (question != null) {
            questionLabel.setText(question.getQuestionText());
            scoreLabel.setText("Score: " + score);
        } else {
            questionLabel.setText("Aucune question trouvée.");
        }
    }

    public void handleSubmit(ActionEvent event) {
        if (question == null || tf_answer.getText().isEmpty()) return;

        String userAnswer = tf_answer.getText().trim();
        if (question.checkAnswer(userAnswer)) {
            score++;
            correctAnswers++;
            streakCount++;
            if (streakCount > longestStreak) {
                longestStreak = streakCount;
            }
            tf_answer.clear();
            loadNextQuestion();
        } else {
            WrongAnswerStorage.addWrongAnswer(question);
            pendu.drawFull();
            endgame();
        }
    }

    public void UpdateCurrentPlayer(String Theme) {
        Player player = Player.getCurrentPlayer();
        if (score > player.getBestSurvivalScore()) {
            player.setBestSurvivalScore(score);
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
