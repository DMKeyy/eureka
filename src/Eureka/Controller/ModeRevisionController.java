package Eureka.Controller;

import java.util.List;

import Eureka.models.SoundEffects;
import Eureka.models.WrongAnswerStorage;
import Eureka.models.QuestionRep.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ModeRevisionController {
    @FXML
    private ListView<String> wrongAnswersList;
    @FXML
    private Button btn_back;


    @FXML
    public void initialize(){

        SoundEffects.addSound(btn_back);

        btn_back.setOnAction(e -> {
            SceneManager.changeScene(e, "ChoseGameMode.fxml");
        });
        wrongAnswersList.getItems().clear();

        List<Question> accumulatedWrongAnswers = WrongAnswerStorage.getWrongAnswers();

        if (!accumulatedWrongAnswers.isEmpty()) {
            for (Question q : accumulatedWrongAnswers) {
                String questionText = q.getQuestionText();
                String correctAnswer = q.getAnswer();
                wrongAnswersList.getItems().add("Question: " + questionText + "    Answer: " + correctAnswer);
            }

            WrongAnswerStorage.clearWrongAnswers();
        }
        else{
            wrongAnswersList.getItems().add("No Wrong Answers Found!");
        }
    }
}

