package Eureka.Controller.gamemodes;

import java.util.Timer;
import java.util.TimerTask;

import Eureka.Controller.core.GameMode;
import Eureka.models.GameData;
import Eureka.models.PlayerRep.Player;
import Eureka.models.QuestionRep.QuestionRepository;
import Eureka.models.QuestionRep.WrongAnswerRepository;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TimeTrialGameModeController extends GameMode {
    private static final int STARTING_TIME = 60;
    private static final int BONUS_TIME = 5;
    private static final int PENALTY_TIME = 2; 

    int streakCount = 0;
    int longestStreak = 0;
    int timeRemaining;
    Timer timer;

    @FXML private AnchorPane root;
    @FXML private Label questionLabel;
    @FXML private TextField tf_answer;
    @FXML private Label scoreText;
    @FXML private Label timerText;
    @FXML private Button btn_submit;
   
    public TimeTrialGameModeController() {
        this.score = 0;
        this.correctAnswers = 0; 
    }

    @FXML
    public void initialize() {

        this.theme = GameData.getTheme();
        this.difficulty = GameData.getDifficulty();
        this.timeRemaining = STARTING_TIME;

        startTimer();
        LoadNextQuestion();
        setupEventHandlers();

    }

    @Override
    protected void setupEventHandlers() {
        btn_submit.setOnAction(this::handleSubmit);
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                if (timeRemaining > 0) {
                    timeRemaining--;
                    updateTimerUI();
                } else {
                    timer.cancel();
                    GameData.setScore(score);
                    endgame();
                }
            });
            }
        }, 1000, 1000);
    }

    public void updatePlayerBestScore(Player player) {
        if (score > player.getBestTimeTrialScore()) {
            player.setBestTimeTrialScore(score);
        }
    }

    public void LoadNextQuestion() {
        question = QuestionRepository.getQuestion(theme, difficulty);
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
            longestStreak = Math.max(streakCount, longestStreak);
            timeRemaining += BONUS_TIME;
        } else {
            WrongAnswerRepository.recordWrongAnswer(Player.getCurrentPlayer().getPlayerId(), question.getQuestion_id());
            streakCount=0;
            timeRemaining -= PENALTY_TIME;
            if (timeRemaining < 0) {
                timeRemaining = 0;
            }
        }

        updateUI();
        tf_answer.clear();
        LoadNextQuestion();
    }

    private void updateUI() {
        scoreText.setText("Score: " + score);
        updateTimerUI();
    }

    private void updateTimerUI() {
        
        timerText.setText("Temps restant: " + timeRemaining + "s");
    }
}