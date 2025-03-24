package Eureka.Controller;

import java.util.List;

import Eureka.models.GameData;
import Eureka.models.Player;
import Eureka.models.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class BasicGameModeController {
    String theme;
    int difficulty;
    int score;
    int correctAnswers;
    PenduDrawer pendu;
    Question question;

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
        btn_submit.setOnAction(event -> handleSubmit());
    }


    public void UpdateCurrentPlayer(String Theme) {
        Player player = Player.getCurrentPlayer();
        if (score > player.getBestScore()) {
            player.setBestScore(score);
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
            default:
                break;
        }

    }

    public void LoadNextQuestion() {
        question = DbController.getQuestion(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
        scoreText.setText("Score: " + score);
    }

    public void handleSubmit() {
        if (question == null) return;

        if (question.checkAnswer(tf_answer.getText())) {
            score++;
            correctAnswers++;
            scoreText.setText("Score: " + score);
        } else {
            pendu.setAttemptsLeft(pendu.getAttemptsLeft() - 1);
            pendu.drawNextPart();

            if (pendu.isGameOver()) {
                UpdateCurrentPlayer(theme);
                DbController.resetUsedQuestions();
                return; 
            }
        }

        tf_answer.clear();
        LoadNextQuestion();
    }


}
