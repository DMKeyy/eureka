package Eureka.Controller.gamemodes;

import Eureka.Controller.ui.Animation;
import Eureka.Controller.core.GameMode;
import Eureka.Controller.ui.SceneManager;
import Eureka.models.GameData;
import Eureka.models.PlayerRep.Player;
import Eureka.models.QuestionRep.QuestionRepository;
import Eureka.models.QuestionRep.WrongAnswerRepository;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TimeTrialGameModeController extends GameMode {

    private static final int STARTING_TIME = 60;
    private static final int BONUS_TIME = 5;
    private static final int PENALTY_TIME = 2;
    private static final long TOTAL_TIME_MS = STARTING_TIME * 1000L;

    @FXML private AnchorPane root;
    @FXML private Label questionLabel;
    @FXML private TextField tf_answer;
    @FXML private Label scoreText;
    @FXML private Label timerText;
    @FXML private Button btn_submit, btn_return;
    @FXML private ImageView ouroboros;

    private long startTimeMs;
    private Timeline timeline;
    private boolean isPulsing = false;

    public TimeTrialGameModeController() {
        this.score = 0;
        this.correctAnswers = 0;
    }

    @FXML
    public void initialize() {
        this.theme = GameData.getTheme();
        this.difficulty = GameData.getDifficulty();
        Animation.spin(ouroboros);
        startTimer();
        LoadNextQuestion();
        setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        btn_submit.setOnAction(this::handleSubmit);
        btn_return.setOnAction(event -> {
            if (timeline != null) {
                timeline.stop();
            }
            SceneManager.changeScene(event, "ChoseGameMode.fxml");
        });
    }

    private void startTimer() {
        startTimeMs = System.currentTimeMillis();
        timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            long elapsed = System.currentTimeMillis() - startTimeMs;
            long remaining = TOTAL_TIME_MS - elapsed;
            if (remaining <= 0) {
                timerText.setText("00.00 s");
                timeline.stop();
                GameData.setScore(score);
                endgame();
            } else {
                updateTimerUI(remaining);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTimerUI(long remainingMs) {
        if (remainingMs > 10_000) {
            long seconds = remainingMs / 1000;
            timerText.setText(seconds + " s");
        } else {
            long seconds = remainingMs / 1000;
            long centi = (remainingMs % 1000) / 10;
            String formatted = String.format("%d.%02d s", seconds, centi);
            timerText.setText(formatted);

            if (!isPulsing) {
                triggerTimeAlert();
            }
        }
    }

    private void triggerTimeAlert() {
        isPulsing = true;
        timerText.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        ScaleTransition pulse = new ScaleTransition(Duration.millis(400), timerText);
        pulse.setFromX(1.0);
        pulse.setToX(1.2);
        pulse.setFromY(1.0);
        pulse.setToY(1.2);
        pulse.setCycleCount(ScaleTransition.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();
    }

    @Override
    public void LoadNextQuestion() {
        question = QuestionRepository.getQuestion(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
        scoreText.setText("Score: " + score);
    }

    public void handleSubmit(ActionEvent e) {
        if (question == null || tf_answer.getText().isEmpty()) return;

        boolean correct = question.checkAnswer(tf_answer.getText());

        if (correct) {
            Animation.bounce(tf_answer);
            Animation.bounce(scoreText);
            Animation.bounce(timerText);
            score++;
            correctAnswers++;
            streakCount++;
            longestStreak = Math.max(streakCount, longestStreak);
            startTimeMs -= BONUS_TIME * 1000L; 
        } else {
            WrongAnswerRepository.recordWrongAnswer(Player.getCurrentPlayer().getPlayerId(), question.getQuestion_id());
            streakCount = 0;
            Animation.shake(tf_answer);
            startTimeMs += PENALTY_TIME * 1000L; 
        }

        updateUI();
        tf_answer.clear();
        LoadNextQuestion();
    }

    private void updateUI() {
        scoreText.setText("Score: " + score);
        long elapsed = System.currentTimeMillis() - startTimeMs;
        long remaining = Math.max(0, TOTAL_TIME_MS - elapsed);
        updateTimerUI(remaining);
    }

    public void updatePlayerBestScore(Player player) {
        if (score > player.getBestTimeTrialScore()) {
            player.setBestTimeTrialScore(score);
        }
    }
}
