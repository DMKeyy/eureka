package Eureka.Controller;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


import Eureka.models.GameData;
import Eureka.models.SoundEffects;
import Eureka.models.WrongAnswerStorage;
import Eureka.models.PlayerRep.Player;
import Eureka.models.PlayerRep.PlayerRepository;
import Eureka.models.QuestionRep.Question;
import Eureka.models.QuestionRep.QuestionRepository;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ProgressiveTimeTrialController  {
    private static int STARTING_TIME = 30;
     

    String theme;
    int difficulty;
    int score;
    int correctAnswers;
    Question question;
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
   
    public  void ProgressiveTimeTrial() {
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
        btn_submit.setOnAction(event -> handleSubmit(event));
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
                    endgame();
                }
            });
            }
        }, 1000, 1000);
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
        question = QuestionRepository.getQuestion(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
        scoreText.setText("Score: " + score);
    }

    public void handleSubmit(ActionEvent e) {
        if (question == null || tf_answer.getText().isEmpty()) return;

        if (question.checkAnswer(tf_answer.getText())) {
            score++;
            correctAnswers++;
            streakCount++;
            longestStreak = Math.max(streakCount, longestStreak);
            
        } else {
            WrongAnswerStorage.addWrongAnswer(question);
            streakCount=0;
            timeRemaining -= 5;
            if (timeRemaining <= 0) {
                timeRemaining = 0;
                updateTimerUI();
                timer.cancel();
                endgame();
                return;
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

    public void endgame() {
        UpdateCurrentPlayer(theme);
        PlayerRepository.updatePlayer(Player.getCurrentPlayer());
        QuestionRepository.resetUsedQuestions();
        SceneManager.showPopup(root, "GameOver.fxml");
    }

}