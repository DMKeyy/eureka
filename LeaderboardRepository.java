package Eureka.models.LeaderBoardRep;

import Eureka.models.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardRepository {

    private Connection connect() {
        return DatabaseService.getConnection();
    }

    private List<LeaderboardEntry> getLeaderboard(String scoreColumnName) {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();

        String sql = "SELECT username, " + scoreColumnName + " AS Score FROM player ORDER BY " + scoreColumnName + " DESC";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("Username");
                int score = rs.getInt("Score"); // Score vient de "AS Score" !

                LeaderboardEntry entry = new LeaderboardEntry(username, score);
                leaderboard.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    // Voici les bons getters !
    public List<LeaderboardEntry> getBasicLeaderboard() {
        return getLeaderboard("Best_score");
    }

    public List<LeaderboardEntry> getSurvivalLeaderboard() {
        return getLeaderboard("Best_survival_score");
    }

    public List<LeaderboardEntry> getTimeTrialLeaderboard() {
        return getLeaderboard("Best_time_trial_score");
    }

    public List<LeaderboardEntry> getProgressiveTimeTrialLeaderboard() {
        return getLeaderboard("Best_Progressive_time_trial_score");
    }

    public List<LeaderboardEntry> getMissingLetterLeaderboard() {
        return getLeaderboard("Best_Missing_Letter_score");
    }

    public List<LeaderboardEntry> getMcqLeaderboard() {
        return getLeaderboard("Best_Mcq_score");
    }

    // Maintenant les correct answers par thème
    public List<LeaderboardEntry> getScienceLeaderboard() {
        return getLeaderboard("Correct_answers_science");
    }

    public List<LeaderboardEntry> getHistoryLeaderboard() {
        return getLeaderboard("Correct_answers_history");
    }

    public List<LeaderboardEntry> getGeographyLeaderboard() {
        return getLeaderboard("Correct_answers_geography");
    }

    public List<LeaderboardEntry> getArtLeaderboard() {
        return getLeaderboard("Correct_answers_art");
    }

    public List<LeaderboardEntry> getSportLeaderboard() {
        return getLeaderboard("Correct_answers_sport");
    }

    public List<LeaderboardEntry> getJavaLeaderboard() {
        return getLeaderboard("Correct_answers_java");
    }

    public List<LeaderboardEntry> getIslamLeaderboard() {
        return getLeaderboard("Correct_answers_islam");
    }
}

