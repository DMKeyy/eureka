package Eureka.Controller;

import java.util.List;
import Eureka.models.GameData;
import Eureka.models.PenduDrawer;
import Eureka.models.WrongAnswerStorage;
import Eureka.models.PlayerRep.Player;
import Eureka.models.PlayerRep.PlayerRepository;
import Eureka.models.QuestionRep.Question;
import Eureka.models.QuestionRep.QuestionRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class GameMode {
    protected String theme;
    protected int difficulty;
    protected int score;
    protected int correctAnswers;
    protected PenduDrawer pendu;
    protected Question question;
    protected int streakCount = 0;
    protected int longestStreak = 0;

    @FXML
    protected AnchorPane root;
    @FXML
    protected Label questionLabel;
    @FXML
    protected Label scoreText;
    @FXML
    protected Button btn_submit;
    @FXML
    protected ImageView headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage, leftfeetImage, rightfeetImage;

    public GameMode() {
        this.score = 0;
        this.correctAnswers = 0;
    }

    @FXML
    public void initialize() {
        this.theme = GameData.getTheme();
        this.difficulty = GameData.getDifficulty();
        setupPendu();
        LoadNextQuestion();
        setupEventHandlers();
    }

    protected void setupPendu() {
        pendu = new PenduDrawer(List.of(headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage, leftfeetImage, rightfeetImage), 8);
        if (pendu == null) {
            throw new IllegalStateException("PenduDrawer not initialized. Make sure setupPendu() is called.");
            
        }
    }

    protected abstract void setupEventHandlers();

    public void UpdateCurrentPlayer(String Theme) {
        Player player = Player.getCurrentPlayer();
        updatePlayerBestScore(player);

        if (longestStreak > player.getStreakCount()) {
            player.setStreakCount(longestStreak);
        }

        updateThemeStats(player, Theme);
        player.setTotalGamesPlayed(player.getTotalGamesPlayed() + 1);
    }

    protected abstract void updatePlayerBestScore(Player player);

    protected void updateThemeStats(Player player, String Theme) {
        switch (Theme) {
            case "Science":
                player.setCorrectAnswersScience(player.getCorrectAnswersScience() + correctAnswers);
                break;
            case "History":
                player.setCorrectAnswersHistory(player.getCorrectAnswersHistory() + correctAnswers);
                break;
            case "Geography":
                player.setCorrectAnswersGeography(player.getCorrectAnswersGeography() + correctAnswers);
                break;
            case "Sport":
                player.setCorrectAnswersSport(player.getCorrectAnswersSport() + correctAnswers);
                break;
            case "Art":
                player.setCorrectAnswersArt(player.getCorrectAnswersArt() + correctAnswers);
                break;
            case "Java":
                player.setCorrectAnswersJava(player.getCorrectAnswersJava() + correctAnswers);
                break;
            case "Islam":
                player.setCorrectAnswersIslam(player.getCorrectAnswersIslam() + correctAnswers);
                break;
            default:
                break;
        }
    }

    protected abstract void LoadNextQuestion();

    protected void handleCorrectAnswer() {
        score++;
        correctAnswers++;
        streakCount++;
        if (streakCount > longestStreak) {
            longestStreak = streakCount;
        }
        updateScore();
    }

    protected void handleWrongAnswer() {
        if (!WrongAnswerStorage.Contains(question)) {
            WrongAnswerStorage.addWrongAnswer(question);
        }
        pendu.setAttemptsLeft(pendu.getAttemptsLeft() - 1);
        pendu.drawNextPart();
        streakCount = 0;

        if (pendu.isGameOver()) {
            endgame();
        }
    }

    protected void updateScore() {
        
        scoreText.setText("Score: " + score);
        GameData.setScore(score);
    }

    public void endgame() {
        UpdateCurrentPlayer(theme);
        PlayerRepository.updatePlayer(Player.getCurrentPlayer());
        QuestionRepository.resetUsedQuestions();
        SceneManager.showPopup(root, "GameOver.fxml");
    }
}