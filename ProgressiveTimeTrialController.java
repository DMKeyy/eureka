package Eureka.Controller.gamemodes;

import Eureka.Controller.core.Animation;
import Eureka.Controller.core.GameMode;
import Eureka.Controller.ui.SceneManager;
import Eureka.models.GameData;
import Eureka.models.PlayerRep.Player;
import Eureka.models.PlayerRep.PlayerRepository;
import Eureka.models.QuestionRep.Question;
import Eureka.models.QuestionRep.QuestionRepository;
import Eureka.models.QuestionRep.WrongAnswerRepository;
import Eureka.models.ThemeRep.Theme;
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

public class ProgressiveTimeTrialController extends GameMode {

    private static final int STARTING_TIME      = 30;       
    private static final int PENALTY_PER_WRONG  = 5;         
    private long baseTimePerQuestion;              
    private long startTimeMs;                      
    private Timeline timeline;
    private boolean  isPulsing = false;

    @FXML private AnchorPane  root;
    @FXML private Label       questionLabel;
    @FXML private TextField   tf_answer;
    @FXML private Label       scoreText;
    @FXML private Label       timerText;
    @FXML private Button      btn_submit, btn_return;
    @FXML private ImageView   ouroboros;

    public ProgressiveTimeTrialController() {
        this.score          = 0;
        this.correctAnswers = 0;
    }

    @FXML
    public void initialize() {
        
        this.theme   = GameData.getTheme();
        this.difficulty = GameData.getDifficulty();

        
        Animation.spin(ouroboros);

        
        this.baseTimePerQuestion = STARTING_TIME;
        startTimer();                
        LoadNextQuestion();         

        setupEventHandlers();        
    }

    @Override
    protected void setupEventHandlers() {
        btn_submit.setOnAction(this::handleSubmit);
        btn_return.setOnAction(evt -> {
            if (timeline != null) timeline.stop();
            SceneManager.changeScene(evt, "ChoseGameMode.fxml");
        });
    }

    private void startTimer() {
        
        startTimeMs = System.currentTimeMillis();
        
        timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            long elapsed   = System.currentTimeMillis() - startTimeMs;
            long remaining = baseTimePerQuestion*1000L - elapsed;
            if (remaining <= 0) {
                
                timerText.setText("0 s");
                timeline.stop();
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
            long sec = remainingMs / 1000;
            timerText.setText(sec + " s");
        } else {
            long sec  = remainingMs / 1000;
            long cent = (remainingMs % 1000) / 10;
            String fmt = String.format("%d.%02d s", sec, cent);
            timerText.setText(fmt);
            if (!isPulsing) {
                Animation.pulseRed(timerText);
                isPulsing = true;
            }
        }
    }

    @Override
    protected void LoadNextQuestion() {
        question = QuestionRepository.getQuestion(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
        scoreText.setText("Score: " + score);
    }

    public void handleSubmit(ActionEvent e) {
        if (question == null || tf_answer.getText().isEmpty()) return;

        timeline.stop();  

        boolean correct = question.checkAnswer(tf_answer.getText());
        if (correct) {
            Animation.bounce(tf_answer);
            Animation.bounce(scoreText);
            Animation.bounce(timerText);

            score++; correctAnswers++; streakCount++;
            longestStreak = Math.max(longestStreak, streakCount);
            
            baseTimePerQuestion = STARTING_TIME;
        } else {
            Animation.shake(tf_answer);
            WrongAnswerRepository
              .recordWrongAnswer(Player.getCurrentPlayer().getPlayerId(), question.getQuestion_id());
            streakCount = 0;
            
            baseTimePerQuestion = Math.max(0, baseTimePerQuestion - PENALTY_PER_WRONG);
            if (baseTimePerQuestion == 0) {
                
                updateTimerUI(0);
                endgame();
                return;
            }
        }

        updateUI();
        tf_answer.clear();
        LoadNextQuestion();

        
        isPulsing = false;
        startTimer();
    }

    private void updateUI() {
        scoreText.setText("Score: " + score);
    }

    @Override
    public void endgame() {
        
        Player player = Player.getCurrentPlayer();
        if (score > player.getBestProgressiveTimeTrialScore())
            player.setBestProgressiveTimeTrialScore(score);
        if (longestStreak > player.getStreakCount())
            player.setStreakCount(longestStreak);

        PlayerRepository.updatePlayer(player);
        QuestionRepository.resetUsedQuestions();
        SceneManager.showPopup(root, "GameOver.fxml");
    }
}
