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
import java.util.Locale;
import java.util.Set;

import Eureka.models.Badge;
import Eureka.models.Badge.BadgeRarity;
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
            System.out.println("Stage: " + stage);
            stage.setScene(new Scene(root));
            Platform.runLater(stage::centerOnScreen);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isUsernameValid(String username) {

        String query = "SELECT username FROM player WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if username exists
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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


    public static boolean updatePassword(String username, String newPassword) {

    
        String query = "UPDATE player SET password = ? WHERE username = ?";
    
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUsername(String oldUsername, String newUsername) {
        String query = "UPDATE player SET username = ? WHERE username = ?";
    
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
                } while (rs.next());
                
                question.setMultipleChoices(choices);
                question.setAnswer(correctAnswer);
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



    public static void resetUsedQuestions() {
        usedQuestionIds.clear();
    }


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
        int BestSurvivalScore = rs.getInt("Best_survival_score");
        int bestTimeTrialScore = rs.getInt("Best_time_trial_score");
        int bestProgressiveTimeTrialScore = rs.getInt("Best_Progressive_time_trial_score");
        int bestMissingLetterScore = rs.getInt("Best_Missing_Letter_score");
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
        int correctAnswersSport = rs.getInt("Correct_answers_sport");
        int badgeCount = rs.getInt("BadgeCount");

        Player player = new Player(rs.getString("Username"), rs.getString("Password"), registrationTime,0, dailyChallengesCompleted, bestScore, BestSurvivalScore,bestTimeTrialScore, bestProgressiveTimeTrialScore,bestMissingLetterScore,totalGamesPlayed, streakCount, longestCompetitionTime, correctAnswersScience, correctAnswersHistory, correctAnswersGeography, correctAnswersSport,correctAnswersArt, correctAnswersJava, correctAnswersIslam, badgeCount);
        Player.setCurrentPlayer(player);
    }
        

    public static void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Selection Required");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void updatePlayer() {
        Player player = Player.getCurrentPlayer();
    
        String query =  "UPDATE player SET " +
                        "best_score = ?, " +
                        "best_survival_score = ?, " +
                        "best_time_trial_score = ?, " +
                        "Best_Progressive_time_trial_score = ?, " +
                        "Best_Missing_Letter_score = ?, " +
                        "streak_count = ?, " +
                        "correct_answers_science = ?, " +
                        "correct_answers_history = ?, " +
                        "correct_answers_geography = ?, " +
                        "correct_answers_art = ?, " +
                        "correct_answers_islam = ?, " +
                        "correct_answers_java = ?, " +
                        "correct_answers_sport = ?, " +
                        "total_game_played = ? " +
                        "WHERE username = ?";
    
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
                pstmt.setInt(1, player.getBestScore());
                pstmt.setInt(2, player.getBestSurvivalScore());
                pstmt.setInt(3, player.getBestTimeTrialScore());
                pstmt.setInt(4, player.getBestProgressiveTimeTrialScore());
                pstmt.setInt(5, player.getBestMissingLetterScore());
                pstmt.setInt(6, player.getStreakCount());
                pstmt.setInt(7, player.getCorrectAnswersScience());
                pstmt.setInt(8, player.getCorrectAnswersHistory());
                pstmt.setInt(9, player.getCorrectAnswersGeography());
                pstmt.setInt(10, player.getCorrectAnswersArt());
                pstmt.setInt(11, player.getCorrectAnswersIslam());
                pstmt.setInt(12, player.getCorrectAnswersJava());
                pstmt.setInt(13, player.getCorrectAnswersSport());
                pstmt.setInt(14, player.getTotalGamesPlayed());
                pstmt.setString(15, player.getUsername());
    
            pstmt.executeUpdate();
            System.out.println("✅ Player stats updated successfully!");
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to update player stats.");
        }
    }

    public static List<Badge> getAllBadges() {
        List<Badge> badges = new ArrayList<>();
        String query = "SELECT * FROM badges";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Récupérer les données de la base de données
                int id = rs.getInt("ID_badge");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int required = rs.getInt("Required_correct_answers");
                String theme = rs.getString("Theme");
                String rarityString = rs.getString("Rarity"); 
                BadgeRarity rarity = BadgeRarity.valueOf(rarityString.toUpperCase(Locale.ROOT));
                // Créer un objet Badge
                Badge badge = new Badge(name, description, required, theme, rarity);
                badge.setBadge_id(id);

                badges.add(badge);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return badges;
    }



    public static boolean playerHasBadge(Player player, Badge badge) {
        String query = "SELECT * FROM player_badges WHERE Username = ? AND ID_badge = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, player.getUsername());
            stmt.setInt(2, badge.getBadge_id());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void assignBadgeToPlayer(Player player, int badgeId) {
        String query = "INSERT IGNORE INTO player_badges (Username, ID_badge) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, player.getUsername());
            stmt.setInt(2, badgeId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Badge> getPlayerBadges(String username) {
        List<Badge> badges = new ArrayList<>();
        String query = "SELECT b.* FROM badges b " +
                    "JOIN player_badges pb ON b.ID_badge = pb.ID_badge " +
                    "WHERE pb.Username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Badge badge = new Badge(
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getInt("Required_correct_answers"),
                        rs.getString("Theme"),
                        Badge.BadgeRarity.valueOf(rs.getString("Rarity").toUpperCase())
                );
                badge.setBadge_id(rs.getInt("ID_badge"));
                badges.add(badge);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return badges;
    }


    public static void updateBadgeCount(String username, int count) {
        String query = "UPDATE player SET BadgeCount = ? WHERE Username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, count);
            stmt.setString(2, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 
}

