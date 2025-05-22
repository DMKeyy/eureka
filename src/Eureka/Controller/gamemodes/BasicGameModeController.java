package Eureka.Controller.gamemodes;

import Eureka.Controller.ui.Animation;
import Eureka.Controller.core.GameMode;
import Eureka.Controller.ui.SceneManager;
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

    btn_return.setOnAction(event -> {
        SceneManager.changeScene(event, "ChoseGameMode.fxml");
    });
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
            Animation.bounce(tf_answer);
            Animation.bounce(scoreText);
        } else {
            handleWrongAnswer();
            Animation.shake(tf_answer);
        }

        tf_answer.clear();
        LoadNextQuestion();
    }
}
