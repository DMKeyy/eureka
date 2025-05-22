package Eureka.Controller.core;

import java.util.List;
import Eureka.models.GameData;
import Eureka.models.PenduDrawer;
import Eureka.models.LeaderBoardRep.ScoreRepository;
import Eureka.models.PlayerRep.Player;
import Eureka.models.PlayerRep.PlayerRepository;
import Eureka.models.QuestionRep.Question;
import Eureka.models.QuestionRep.QuestionRepository;
import Eureka.models.QuestionRep.WrongAnswerRepository;
import Eureka.models.ThemeRep.Theme;
import Eureka.models.ThemeRep.ThemeRepository;
import Eureka.Controller.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class GameMode {
    protected Theme theme;
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
    protected Button btn_return;
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

    public void UpdateCurrentPlayer(Theme Theme) {
        Player player = Player.getCurrentPlayer();

        if (longestStreak > player.getStreakCount()) {
            player.setStreakCount(longestStreak);
        }
        player.setTotalGamesPlayed(player.getTotalGamesPlayed() + 1);

        PlayerRepository.updatePlayer(player);
        ThemeRepository.updateThemeStats(player, Theme, correctAnswers);
        ScoreRepository.updateScore(player, GameData.getMode(), score);
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
        WrongAnswerRepository.recordWrongAnswer(Player.getCurrentPlayer().getPlayerId(), question.getQuestion_id());
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