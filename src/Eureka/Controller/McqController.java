package Eureka.Controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import Eureka.models.GameData;
import Eureka.models.PenduDrawer;
import Eureka.models.SoundEffects;
import Eureka.models.WrongAnswerStorage;
import Eureka.models.PlayerRep.Player;
import Eureka.models.PlayerRep.PlayerRepository;
import Eureka.models.QuestionRep.Question;
import Eureka.models.QuestionRep.QuestionRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class McqController {
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
    private Label scoreText;
    @FXML
    private Button btn_answer1;
    @FXML
    private Button btn_answer2;
    @FXML
    private Button btn_answer3;
    @FXML
    private Button btn_answer4;
    @FXML 
    private ImageView headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage ,leftfeetImage, rightfeetImage;
    
    public McqController() {
        this.score = 0;
        this.correctAnswers = 0;
    }


    @FXML
    public void initialize() {

        this.theme = GameData.getTheme();
        this.difficulty = GameData.getDifficulty();
        pendu = new PenduDrawer(List.of(headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage, leftfeetImage, rightfeetImage), 8);


        LoadNextQuestion();
        btn_answer1.setOnAction(event -> handleSubmit(event));
        btn_answer2.setOnAction(event -> handleSubmit(event));
        btn_answer3.setOnAction(event -> handleSubmit(event));
        btn_answer4.setOnAction(event -> handleSubmit(event));
    }


    public void UpdateCurrentPlayer(String Theme) {
        Player player = Player.getCurrentPlayer();
        if (score > player.getBestMcqScore()) {
            player.setBestMcqScore(score);
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
        question = QuestionRepository.getQuestionMCQ(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
        List<String> choices = question.getMultipleChoices();
        Collections.shuffle(choices);
        btn_answer1.setText(choices.get(0)); 
        btn_answer2.setText(choices.get(1)); 
        btn_answer3.setText(choices.get(2)); 
        btn_answer4.setText(choices.get(3));
        scoreText.setText("Score: " + score);
    }

    public void handleSubmit(ActionEvent e) {
        if (question == null) return;
        Button clickedButton = (Button) e.getSource();
        if (question.checkAnswer(clickedButton.getText())) {
            score++;
            correctAnswers++;
            streakCount++;
            if (streakCount > longestStreak) {
                longestStreak = streakCount;
            }

            scoreText.setText("Score: " + score);
        } else {
            WrongAnswerStorage.addWrongAnswer(question);
            pendu.setAttemptsLeft(pendu.getAttemptsLeft() - 1);
            pendu.drawNextPart();
            streakCount = 0;

            if (pendu.isGameOver()) {
                endgame();
                return;
            }
        }

        
        LoadNextQuestion();
    }

    public void endgame() {
        UpdateCurrentPlayer(theme);
        PlayerRepository.updatePlayer(Player.getCurrentPlayer());
        QuestionRepository.resetUsedQuestions();

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
