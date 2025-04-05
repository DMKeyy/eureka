package Eureka.Controller;

import Eureka.models.GameData;
import Eureka.models.Question;
import Eureka.models.WrongAnswerStorage;

import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LocalMultiplayerController {

    // Statics pour suivre la progression et le score d'une partie à l'autre
    private static int scoreJ1 = 0;
    int scoreJ2 = 0;
    int mistakesJ1 = 0;      // nombre d'erreurs déjà faites par J1
    int attemptsLeftJ1 = 8;
    PenduDrawer pendu1;
    PenduDrawer pendu2;
    Boolean CurrentPlayer = true; // true = J1, false = J2

    @FXML private AnchorPane root;

    @FXML private Label questionLabel, currentPlayerLabel, scoreText1, scoreText2,CurrentTour;
    @FXML private TextField tf_answer;
    @FXML private Button btn_submit;
    @FXML private Button btn_nextPlayer; // bouton "Fin du tour"
    @FXML private ImageView headImage, bodyImage, leftArmImage, rightArmImage,
                           leftLegImage, rightLegImage, leftfeetImage, rightfeetImage;
    @FXML private ImageView headImage1, bodyImage1, leftArmImage1, rightArmImage1,
                           leftLegImage1, rightLegImage1, leftfeetImage1, rightfeetImage1;   
    

    private Question currentQuestion;
    private String theme;
    private int difficulty;
    private int TourCurrent = 1;

    @FXML
    public void initialize() {
        // 1) Récupère les infos
        theme = GameData.getTheme();
        difficulty = GameData.getDifficulty();

        pendu1 = new PenduDrawer(List.of(headImage, bodyImage, leftArmImage, rightArmImage, leftLegImage, rightLegImage, leftfeetImage, rightfeetImage), 8);
        pendu2 = new PenduDrawer(List.of(headImage1, bodyImage1, leftArmImage1, rightArmImage1, leftLegImage1, rightLegImage1, leftfeetImage1, rightfeetImage1), 8);

        currentPlayerLabel.getStyleClass().removeAll("tour-blue", "tour-red");
        currentPlayerLabel.getStyleClass().add("tour-blue");
      

        CurrentTour.setText("Tour " + TourCurrent);
       
        currentPlayerLabel.setText("Tour de Joueur 1");
       
        scoreText1.setText("Joueur 1 : " + scoreJ1);
        scoreText2.setText("Joueur 2 : " + scoreJ2);

        // 6) Charge la question
        loadNextQuestion();

        // 7) Boutons
        btn_submit.setOnAction(this::handleSubmit);
    }

    private void loadNextQuestion() {
        currentQuestion = DbController.getQuestion(theme, difficulty);
        if (currentQuestion != null) {
            questionLabel.setText(currentQuestion.getQuestionText());
        } else {
            questionLabel.setText("Aucune question disponible pour ce thème / difficulté.");
        }
    }

    private void handleSubmit(ActionEvent e) {
        if (currentQuestion == null || tf_answer.getText().isEmpty()) return;

        if(CurrentPlayer){
           
            currentPlayerLabel.getStyleClass().removeAll("tour-blue", "tour-red");
            currentPlayerLabel.getStyleClass().add("tour-red");
            currentPlayerLabel.setText("Tour de Joueur 2");
            CurrentTour.setText("Tour " + TourCurrent);
        boolean correct = currentQuestion.checkAnswer(tf_answer.getText().trim());
        if (correct) {
            // Bonne réponse
            scoreJ1++;
            scoreText1.setText("Joueur 1 : " + scoreJ1);
        } else {
            // Mauvaise réponse => nouvelle erreur
            WrongAnswerStorage.addWrongAnswer(currentQuestion);
            pendu1.setAttemptsLeft(pendu1.getAttemptsLeft() - 1);
            pendu1.drawNextPart();


            // Vérifie si le pendu est complet
            if (pendu1.isGameOver()) {
                endGame("Joueur 1 a perdu ! Joueur 2 gagne !");
                return;
            }
        }
        tf_answer.clear();
        loadNextQuestion();
        CurrentPlayer = false;

    }else{
        TourCurrent++;
        CurrentTour.setText("Tour " + TourCurrent);
        currentPlayerLabel.getStyleClass().removeAll("tour-blue", "tour-red");
        currentPlayerLabel.getStyleClass().add("tour-blue");
        currentPlayerLabel.setText("Tour de Joueur 1");
       
        boolean correct = currentQuestion.checkAnswer(tf_answer.getText().trim());
        if (correct) {
            // Bonne réponse
            scoreJ2++;
            scoreText2.setText("Joueur 2 : " + scoreJ2);
        } else {
            WrongAnswerStorage.addWrongAnswer(currentQuestion);
            // Mauvaise réponse => nouvelle erreur
            pendu2.setAttemptsLeft(pendu2.getAttemptsLeft() - 1);
            pendu2.drawNextPart();

            // Vérifie si le pendu est complet
            if (pendu2.isGameOver()) {
                endGame("Joueur 2 a perdu ! Joueur 1 gagne !");
                return;
            }
        }
        tf_answer.clear();
        loadNextQuestion();
        CurrentPlayer = true;
        

    }
    }


    private void endGame(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Eureka/View/fxml/GameOver.fxml"));
            AnchorPane gameover = loader.load();

            GameOverController controller = loader.getController();
            controller.setGameOverMessage(message);

            controller.setScore(scoreJ1);

            root.getChildren().add(gameover);
            gameover.requestFocus();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
