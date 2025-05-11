package Eureka.models.LeaderBoardRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Eureka.models.DatabaseService;
import Eureka.models.GameModeRep.GameModeRepository;
import Eureka.models.ThemeRep.ThemeRepository;

public class LeaderboardRepository {

    public List<LeaderboardEntry> getBasicLeaderboard() {
        return getTopScoresByMode(GameModeRepository.getGameModeByName("Basic").getModeId(), 10);
    }

    public List<LeaderboardEntry> getSurvivalLeaderboard() {
        return getTopScoresByMode(GameModeRepository.getGameModeByName("Survival").getModeId(), 10);
    }

    public List<LeaderboardEntry> getTimeTrialLeaderboard() {
        return getTopScoresByMode(GameModeRepository.getGameModeByName("TimeTrial").getModeId(), 10);
    }

    public List<LeaderboardEntry> getProgressiveTimeTrialLeaderboard() {
        return getTopScoresByMode(GameModeRepository.getGameModeByName("ProgressiveTimeTrial").getModeId(), 10);
    }

    public List<LeaderboardEntry> getMissingLetterLeaderboard() {
        return getTopScoresByMode(GameModeRepository.getGameModeByName("MissingLetters").getModeId(), 10);
    }

    public List<LeaderboardEntry> getMcqLeaderboard() {
        return getTopScoresByMode(GameModeRepository.getGameModeByName("Mcq").getModeId(), 10);
    }

    public List<LeaderboardEntry> getScienceLeaderboard() {
        return getTopScoresByTheme(ThemeRepository.getThemeByName("Science").getThemeId(), 10);
    }

    public List<LeaderboardEntry> getHistoryLeaderboard() {
        return getTopScoresByTheme(ThemeRepository.getThemeByName("History").getThemeId(), 10);
    }

    public List<LeaderboardEntry> getGeographyLeaderboard() {
        return getTopScoresByTheme(ThemeRepository.getThemeByName("Geography").getThemeId(), 10);
    }

    public List<LeaderboardEntry> getSportLeaderboard() {
        return getTopScoresByTheme(ThemeRepository.getThemeByName("Sport").getThemeId(), 10);
    }

    public List<LeaderboardEntry> getArtLeaderboard() {
        return getTopScoresByTheme(ThemeRepository.getThemeByName("Art").getThemeId(), 10);
    }

    public List<LeaderboardEntry> getJavaLeaderboard() {
        return getTopScoresByTheme(ThemeRepository.getThemeByName("Java").getThemeId(), 10);
    }

    public List<LeaderboardEntry> getIslamLeaderboard() {
        return getTopScoresByTheme(ThemeRepository.getThemeByName("Islam").getThemeId(), 10);
    }

    private static List<LeaderboardEntry> getTopScoresByMode(int modeId, int limit) {
        List<LeaderboardEntry> entries = new ArrayList<>();
        String query = "SELECT p.username, s.score FROM player p JOIN scores s ON p.player_id = s.id_player WHERE s.id_mode = ? ORDER BY s.score DESC LIMIT ?";

        try (Connection conn = DatabaseService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, modeId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                entries.add(new LeaderboardEntry(rs.getString("username"), rs.getInt("score")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

    private static List<LeaderboardEntry> getTopScoresByTheme(int themeId, int limit) {
        List<LeaderboardEntry> entries = new ArrayList<>();
        String query = "SELECT p.username, ts.score FROM player p JOIN correct_answers_scores ts ON p.player_id = ts.id_player WHERE ts.id_theme = ? ORDER BY ts.score DESC LIMIT ?";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, themeId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                entries.add(new LeaderboardEntry(rs.getString("username"), rs.getInt("score")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
