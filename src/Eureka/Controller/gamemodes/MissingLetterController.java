package Eureka.Controller.gamemodes;

import java.util.List;

import Eureka.models.GameData;
import Eureka.models.PenduDrawer;
import Eureka.models.PlayerRep.Player;
import Eureka.models.QuestionRep.QuestionRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import Eureka.Controller.core.GameMode;

public class MissingLetterController extends GameMode {

    @FXML
    private TextField tf_answer;

    @FXML
    private Label answerLabel;
    public MissingLetterController() {
        this.score = 0;
        this.correctAnswers = 0;
    }

    @FXML
    public void initialize() {
        this.theme = GameData.getTheme();
        this.difficulty = GameData.getDifficulty();
        pendu = new PenduDrawer(List.of(headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage, leftfeetImage, rightfeetImage), 8);

        LoadNextQuestion();
        setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        btn_submit.setOnAction(event -> handleSubmit(event));
    }

    @Override
    protected void updatePlayerBestScore(Player player) {
        if (score > player.getBestMissingLetterScore()) {
            player.setBestMissingLetterScore(score);
        }
    }

    
    private String hideLetters(String answer) {
        char[] chars = answer.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                chars[i] = '_';
            }
        }
        return new String(chars);
    }

    @Override
    protected void LoadNextQuestion() {
        question = QuestionRepository.getQuestion(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
        answerLabel.setText(hideLetters(question.getAnswer()));
        updateScore();
    }

    public void handleSubmit(ActionEvent e) {
        if (question == null || tf_answer.getText().isEmpty()) return;

        if (question.checkAnswer(tf_answer.getText())) {
            handleCorrectAnswer();
        } else {
            handleWrongAnswer();
        }

        tf_answer.clear();
        LoadNextQuestion();
    }
}