package Eureka.Controller;

import Eureka.models.PlayerRep.Player;
import Eureka.models.QuestionRep.QuestionRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BasicGameModeController extends GameMode {
    @FXML 
    private TextField tf_answer;

    @Override
    protected void setupEventHandlers() {
        btn_submit.setOnAction(event -> handleSubmit(event));
    }

    @Override
    protected void updatePlayerBestScore(Player player) {
        if (score > player.getBestScore()) {
            player.setBestScore(score);
        }
    }

    @Override
    protected void LoadNextQuestion() {
        question = QuestionRepository.getQuestion(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
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
