package Eureka.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Eureka.models.Player;
import Eureka.models.Question;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DbController {
    private static final String URL = "jdbc:mysql://localhost:3306/bdd"; // Update DB name
    private static final String USER = "root";  // Your MySQL username
    private static final String PASSWORD = "";

    private static Set<Integer> usedQuestionIds = new HashSet<>();

  
    public static void changeScene(ActionEvent event, String fxml) {
        try {
            Parent root = FXMLLoader.load(DbController.class.getResource("/Eureka/View/fxml/" + fxml));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Platform.runLater(stage::centerOnScreen);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void signUpUser(ActionEvent event, String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement psCheckUserExist = connection.prepareStatement("SELECT * FROM player WHERE Username = ?");
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO player(Username,Password,Registration_Time) VALUES(?,?,?)")) {

            psCheckUserExist.setString(1, username);
            try (ResultSet rs = psCheckUserExist.executeQuery()) {
                if (rs.isBeforeFirst()) {
                    showAlert(AlertType.ERROR, "Username already exists");
                } else {
                    psInsert.setString(1, username);
                    psInsert.setString(2, password);
                    psInsert.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                    psInsert.executeUpdate();
                    Player.setCurrentPlayer(new Player(username, password, LocalDate.now()));
                    changeScene(event, "LoggedIn.fxml");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // User Login
    public static void loginUser(ActionEvent event, String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement psCheckUserExist = connection.prepareStatement("SELECT * FROM player WHERE Username = ?")) {

            psCheckUserExist.setString(1, username);
            try (ResultSet rs = psCheckUserExist.executeQuery()) {
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        String dbPassword = rs.getString("Password");
                        if (dbPassword.equals(password)) {
                            loadPlayerData(rs);
                            changeScene(event, "LoggedIn.fxml");
                        } else {
                            showAlert(AlertType.ERROR, "Username or password is incorrect");
                        }
                    }
                } else {
                    showAlert(AlertType.ERROR, "Username or password is incorrect, if you don't have an account please sign up");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch Question
    public static Question getQuestion(String theme, int difficulty) {
        Question question = null;
        String query = "SELECT q.ID_question, q.Question_text, m.Choice_text FROM questions q JOIN multiple_choices m ON q.ID_question = m.ID_question WHERE q.Theme = ? AND q.Difficulty_level = ? AND m.Is_correct = 1 "; 

        if (!usedQuestionIds.isEmpty()) {
            query += "AND q.ID_question NOT IN (" + getUsedIdsPlaceholder() + ") ";
        }
        query += "ORDER BY RAND() LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, theme);
            stmt.setInt(2, difficulty);
            setUsedIds(stmt, 3);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    question = new Question();
                    question.setQuestion_id(rs.getInt("ID_question"));
                    question.setQuestionText(rs.getString("Question_text"));
                    question.setAnswer(rs.getString("Choice_text"));
                    usedQuestionIds.add(question.getQuestion_id());
                }
                else{
                    resetUsedQuestions();
                    return getQuestion(theme, difficulty);
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }


    public static Question getQuestionQCM(String theme, int difficulty) {
    Question question = null;
    String query = "SELECT q.ID_question, q.Question_text, m.Choice_text, m.Is_correct " +
                   "FROM questions q " +
                   "JOIN multiple_choices m ON q.ID_question = m.ID_question " +
                   "WHERE q.Theme = ? AND q.Difficulty_level = ? ";
    
    if (!usedQuestionIds.isEmpty()) {
        query += "AND q.ID_question NOT IN (" + getUsedIdsPlaceholder() + ") ";
    }
    query += "ORDER BY RAND()";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, theme);
        stmt.setInt(2, difficulty);
        setUsedIds(stmt, 3);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                question = new Question();
                question.setQuestion_id(rs.getInt("ID_question"));
                question.setQuestionText(rs.getString("Question_text"));
                usedQuestionIds.add(question.getQuestion_id());

                List<String> choices = new ArrayList<>();
                String correctAnswer = null;

                do {
                    String choice = rs.getString("Choice_text");
                    choices.add(choice);
                    if (rs.getBoolean("Is_correct")) {
                        correctAnswer = choice;
                    }
                } while (rs.next()); // Get all choices for this question
                
                question.setMultipleChoices(choices); // Store all choices
                question.setAnswer(correctAnswer); // Store the correct answer
            } else {
                resetUsedQuestions();
                return getQuestionQCM(theme, difficulty);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return question;
}


    // Reset Used Questions
    public static void resetUsedQuestions() {
        usedQuestionIds.clear();
    }

    // Helper Methods
    private static String getUsedIdsPlaceholder() {
    return usedQuestionIds.isEmpty() ? "NULL" : String.join(",", Collections.nCopies(usedQuestionIds.size(), "?"));
    }

    private static void setUsedIds(PreparedStatement stmt, int startIndex) throws SQLException {
        int index = startIndex;
        for (int id : usedQuestionIds) {
            stmt.setInt(index++, id);
        }
    }

    private static void loadPlayerData(ResultSet rs) throws SQLException {
        LocalDate registrationTime = rs.getDate("Registration_time").toLocalDate();
        int bestScore = rs.getInt("Best_score");
        int dailyChallengesCompleted = rs.getInt("Daily_challenge_completed");
        int totalGamesPlayed = rs.getInt("Total_game_played");
        int streakCount = rs.getInt("Streak_count");
        LocalTime longestCompetitionTime = rs.getTime("Longest_competition_time").toLocalTime();
        int correctAnswersScience = rs.getInt("Correct_answers_science");
        int correctAnswersHistory = rs.getInt("Correct_answers_history");
        int correctAnswersGeography = rs.getInt("Correct_answers_geography");
        int correctAnswersArt = rs.getInt("Correct_answers_art");
        int correctAnswersIslam = rs.getInt("Correct_answers_islam");
        int correctAnswersJava = rs.getInt("Correct_answers_java");

        Player player = new Player(rs.getString("Username"), rs.getString("Password"), registrationTime,0, dailyChallengesCompleted, bestScore, totalGamesPlayed, streakCount, longestCompetitionTime, correctAnswersScience, correctAnswersHistory, correctAnswersGeography, correctAnswersArt, correctAnswersIslam, correctAnswersJava);
        Player.setCurrentPlayer(player);
    }

    public static void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Selection Required");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
