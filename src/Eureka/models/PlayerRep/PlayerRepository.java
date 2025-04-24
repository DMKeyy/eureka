package Eureka.models.PlayerRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import Eureka.models.DatabaseService;

public class PlayerRepository {
    
    public static boolean isUsernameValid(String username) {
        String query = "SELECT username FROM player WHERE username = ?";
        
        try (Connection conn = DatabaseService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void signUpUser(String username, String password) throws SQLException {
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement psCheckUserExist = connection.prepareStatement("SELECT * FROM player WHERE Username = ?");
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO player(Username,Password,Registration_Time) VALUES(?,?,?)")) {

            psCheckUserExist.setString(1, username);
            try (ResultSet rs = psCheckUserExist.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    psInsert.setString(1, username);
                    psInsert.setString(2, password);
                    psInsert.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                    psInsert.executeUpdate();
                    Player.setCurrentPlayer(new Player(username, password, LocalDate.now()));
                } else {
                    throw new SQLException("Username already exists");
                }
            }
        }
    }

    public static Player loginUser(String username, String password) throws SQLException {
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement psCheckUserExist = connection.prepareStatement("SELECT * FROM player WHERE Username = ?")) {

            psCheckUserExist.setString(1, username);
            try (ResultSet rs = psCheckUserExist.executeQuery()) {
                if (rs.next()) {
                    String dbPassword = rs.getString("Password");
                    if (dbPassword.equals(password)) {
                        return loadPlayerData(rs);
                    }
                }
                throw new SQLException("Invalid username or password");
            }
        }
    }

    public static boolean updatePassword(String username, String newPassword) {
        String query = "UPDATE player SET password = ? WHERE username = ?";
    
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUsername(String oldUsername, String newUsername) {
        String query = "UPDATE player SET username = ? WHERE username = ?";
    
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void updatePlayer(Player player) {
        String query =  "UPDATE player SET " +
                        "best_score = ?, " +
                        "best_survival_score = ?, " +
                        "best_time_trial_score = ?, " +
                        "Best_Progressive_time_trial_score = ?, " +
                        "Best_Missing_Letter_score = ?, " +
                        "Best_Mcq_score = ?, " +
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
    
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            pstmt.setInt(1, player.getBestScore());
            pstmt.setInt(2, player.getBestSurvivalScore());
            pstmt.setInt(3, player.getBestTimeTrialScore());
            pstmt.setInt(4, player.getBestProgressiveTimeTrialScore());
            pstmt.setInt(5, player.getBestMissingLetterScore());
            pstmt.setInt(6, player.getBestMcqScore());
            pstmt.setInt(7, player.getStreakCount());
            pstmt.setInt(8, player.getCorrectAnswersScience());
            pstmt.setInt(9, player.getCorrectAnswersHistory());
            pstmt.setInt(10, player.getCorrectAnswersGeography());
            pstmt.setInt(11, player.getCorrectAnswersArt());
            pstmt.setInt(12, player.getCorrectAnswersIslam());
            pstmt.setInt(13, player.getCorrectAnswersJava());
            pstmt.setInt(14, player.getCorrectAnswersSport());
            pstmt.setInt(15, player.getTotalGamesPlayed());
            pstmt.setString(16, player.getUsername());
    
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Player loadPlayerData(ResultSet rs) throws SQLException {
        LocalDate registrationTime = rs.getDate("Registration_time").toLocalDate();
        int bestScore = rs.getInt("Best_score");
        int BestSurvivalScore = rs.getInt("Best_survival_score");
        int bestTimeTrialScore = rs.getInt("Best_time_trial_score");
        int bestProgressiveTimeTrialScore = rs.getInt("Best_Progressive_time_trial_score");
        int bestMissingLetterScore = rs.getInt("Best_Missing_Letter_score");
        int bestMcqScore = rs.getInt("Best_Mcq_score");
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

        return new Player(rs.getString("Username"), 
                      rs.getString("Password"), 
                      registrationTime,
                      0, 
                      dailyChallengesCompleted, 
                      bestScore, 
                      BestSurvivalScore,
                      bestTimeTrialScore, 
                      bestProgressiveTimeTrialScore,
                      bestMissingLetterScore,
                      bestMcqScore,
                      totalGamesPlayed, 
                      streakCount, 
                      longestCompetitionTime, 
                      correctAnswersScience, 
                      correctAnswersHistory, 
                      correctAnswersGeography, 
                      correctAnswersSport,
                      correctAnswersArt, 
                      correctAnswersJava, 
                      correctAnswersIslam, 
                      badgeCount);
    }
}