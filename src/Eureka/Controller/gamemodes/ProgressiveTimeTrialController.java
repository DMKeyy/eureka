package Eureka.Controller.gamemodes;

import java.util.Timer;
import java.util.TimerTask;

import Eureka.Controller.core.GameMode;
import Eureka.Controller.ui.SceneManager;
import Eureka.models.GameData;
import Eureka.models.WrongAnswerStorage;
import Eureka.models.PlayerRep.Player;
import Eureka.models.PlayerRep.PlayerRepository;
import Eureka.models.QuestionRep.Question;
import Eureka.models.QuestionRep.QuestionRepository;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ProgressiveTimeTrialController {
    private static int STARTING_TIME = 30;

    String theme;
    int difficulty;
    int score;
    int correctAnswers;
    Question question;
    int streakCount = 0;
    int longestStreak = 0;
    int baseTimePerQuestion;
    int timeRemaining;
    Timer timer;

    @FXML
    private AnchorPane root;
    @FXML
    private Label questionLabel;
    @FXML
    private TextField tf_answer;
    @FXML
    private Label scoreText;
    @FXML
    private Label timerText;
    @FXML
    private Button btn_submit;

    public void ProgressiveTimeTrial() {
        this.score = 0;
        this.correctAnswers = 0;
    }

    @FXML
    public void initialize() {

        this.theme = GameData.getTheme();
        this.difficulty = GameData.getDifficulty();
        this.baseTimePerQuestion = STARTING_TIME;
        this.timeRemaining = baseTimePerQuestion;

        startTimer();

        LoadNextQuestion();
        btn_submit.setOnAction(event -> handleSubmit(event));
    }

    private void startTimer() {
        stopTimer();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    if (timeRemaining > 0) {
                        timeRemaining--;
                        updateTimerUI();
                    } else {
                        stopTimer();
                        endgame();
                    }
                });
            }
        }, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void UpdateCurrentPlayer(String Theme) {
        Player player = Player.getCurrentPlayer();
        if (score > player.getBestProgressiveTimeTrialScore()) {
            player.setBestProgressiveTimeTrialScore(score);
        }

        if (longestStreak > player.getStreakCount()) { // Store longest streak
            player.setStreakCount(longestStreak);
        }

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

        player.setTotalGamesPlayed(player.getTotalGamesPlayed() + 1);

    }

    public void LoadNextQuestion() {
        question = QuestionRepository.getQuestion(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
    }

    public void handleSubmit(ActionEvent e) {
        if (question == null || tf_answer.getText().isEmpty())
            return;

        stopTimer();

        if (question.checkAnswer(tf_answer.getText())) {
            score++;
            correctAnswers++;
            streakCount++;
            scoreText.setText("Score: " + score);
            longestStreak = Math.max(streakCount, longestStreak);
            timeRemaining = baseTimePerQuestion;

        } else {
            WrongAnswerStorage.addWrongAnswer(question);
            streakCount = 0;
            baseTimePerQuestion -= 5;
            if (baseTimePerQuestion <= 0) {
                baseTimePerQuestion = 0;
                updateTimerUI();
                endgame();
                return;
            }
            timeRemaining = baseTimePerQuestion;

        }
        updateUI();
        tf_answer.clear();
        LoadNextQuestion();

        timeRemaining = baseTimePerQuestion;
        startTimer();
    }

    private void updateUI() {
        scoreText.setText("Score: " + score);
        updateTimerUI();
    }

    private void updateTimerUI() {

        timerText.setText("Temps restant: " + timeRemaining + "s");
    }

    public void endgame() {
        UpdateCurrentPlayer(theme);
        PlayerRepository.updatePlayer(Player.getCurrentPlayer());
        QuestionRepository.resetUsedQuestions();
        SceneManager.showPopup(root, "GameOver.fxml");
    }

}