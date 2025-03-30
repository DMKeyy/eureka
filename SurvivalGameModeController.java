package Eureka.Controller;

import Eureka.models.GameData;
import Eureka.models.Player;
import Eureka.models.Question;
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
    private ImageView headImage, bodyImage, leftArmImage, rightArmImage,
            leftLegImage, rightLegImage, leftfeetImage, rightfeetImage;

    private PenduDrawer pendu;
    private Question question;
    private int score = 0;
    private int correctAnswers = 0;
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
            tf_answer.clear();
            loadNextQuestion();
        } else {
            pendu.drawFull();
            endGame();
        }
    }

    public void endGame() {
        Player player = Player.getCurrentPlayer();
        if (score > player.getBestSurvivalScore()) {
            player.setBestSurvivalScore(score);
        }

        switch (theme) {
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
        }

        DbController.resetUsedQuestions();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Eureka/View/fxml/GameOver.fxml"));
            AnchorPane gameover = loader.load();

            GameOverController controller = loader.getController();
            controller.setScore(score);

            gameover.setLayoutX((root.getWidth() - gameover.getPrefWidth()) / 2);
            gameover.setLayoutY((root.getHeight() - gameover.getPrefHeight()) / 2);
            root.getChildren().add(gameover);
            gameover.requestFocus();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
