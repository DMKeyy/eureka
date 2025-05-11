package Eureka.Controller.gamemodes;

import Eureka.Controller.core.GameMode;
import Eureka.models.GameData;
import Eureka.models.PenduDrawer;
import Eureka.models.PlayerRep.Player;
import Eureka.models.QuestionRep.QuestionRepository;
import Eureka.models.QuestionRep.WrongAnswerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class SurvivalGameModeController extends GameMode {

    @FXML
    private AnchorPane root;

    @FXML
    private Label questionLabel;

    @FXML
    private TextField tf_answer;

    @FXML
    private Label scoreText;

    @FXML
    private Button btn_submit;

    @FXML
    private ImageView headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage, leftfeetImage, rightfeetImage;

    private PenduDrawer pendu;

    @FXML
    public void initialize() {
        theme = GameData.getTheme();
        difficulty = GameData.getDifficulty();

        pendu = new PenduDrawer(List.of(headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage, leftfeetImage, rightfeetImage), 8);
        pendu.reset();

        LoadNextQuestion();
        setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        btn_submit.setOnAction(this::handleSubmit);
    }

    @Override
    protected void LoadNextQuestion() {
        question = QuestionRepository.getQuestion(theme, difficulty);
        questionLabel.setText(question.getQuestionText());
        updateScore();
    }

    public void handleSubmit(ActionEvent event) {
        if (question == null || tf_answer.getText().isEmpty()) return;

        String userAnswer = tf_answer.getText().trim();
        if (question.checkAnswer(userAnswer)) {
            handleCorrectAnswer();
            tf_answer.clear();
            LoadNextQuestion();
        } else {
            WrongAnswerRepository.recordWrongAnswer(Player.getCurrentPlayer().getPlayerId(), question.getQuestion_id());    
            pendu.drawFull();
            endgame();
        }
    }
}