package Eureka.Controller.gamemodes;

import java.util.Collections;
import java.util.List;

import Eureka.Controller.core.GameMode;
import Eureka.models.QuestionRep.QuestionRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class McqController extends GameMode {

    @FXML
    private Button btn_answer1;
    @FXML
    private Button btn_answer2;
    @FXML
    private Button btn_answer3;
    @FXML
    private Button btn_answer4;


    @Override
    protected void setupEventHandlers() {
        btn_answer1.setOnAction(event -> handleSubmit(event));
        btn_answer2.setOnAction(event -> handleSubmit(event));
        btn_answer3.setOnAction(event -> handleSubmit(event));
        btn_answer4.setOnAction(event -> handleSubmit(event));
    }

    @Override
    protected void LoadNextQuestion() {
        question = QuestionRepository.getQuestionMCQ(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
        List<String> choices = question.getMultipleChoices();
        Collections.shuffle(choices);
        btn_answer1.setText(choices.get(0));
        btn_answer2.setText(choices.get(1));
        btn_answer3.setText(choices.get(2));
        btn_answer4.setText(choices.get(3));
        updateScore();
    }

    public void handleSubmit(ActionEvent e) {
        if (question == null) return;
        Button clickedButton = (Button) e.getSource();

        if (question.checkAnswer(clickedButton.getText())) {
            handleCorrectAnswer();
        } else {
            handleWrongAnswer();
        }

        LoadNextQuestion();
    }
}
