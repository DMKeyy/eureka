package Eureka.models.ThemeRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Eureka.models.DatabaseService;
import Eureka.models.PlayerRep.Player;

public class ThemeRepository {
    
    public static List<Theme> getAllThemes() {
        List<Theme> themes = new ArrayList<>();
        String query = "SELECT * FROM themes";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Theme theme = new Theme(
                    rs.getInt("id_theme"),
                    rs.getString("nom")
                );
                themes.add(theme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

    public static Theme getThemeById(int themeId) {
        String query = "SELECT id_theme, nom FROM themes WHERE id_theme = ?";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, themeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Theme(
                        rs.getInt("id_theme"),
                        rs.getString("nom")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Theme getThemeByName(String name) {
        String query = "SELECT id_theme, nom FROM themes WHERE nom = ?";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Theme(
                        rs.getInt("id_theme"),
                        rs.getString("nom")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateThemeStats(Player player, Theme theme, int correctAnswers) {
        String query = "INSERT INTO correct_answers_scores (id_player, id_theme, score) VALUES (?, ?, ?) " +
                      "ON DUPLICATE KEY UPDATE score = score + VALUES(score)";
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, player.getPlayerId());
            stmt.setInt(2, theme.getThemeId());
            stmt.setInt(3, correctAnswers);
            stmt.executeUpdate();
            player.addThemeStats(theme, correctAnswers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initializePlayerThemeStats(int playerId) throws SQLException {
        String query = "INSERT INTO correct_answers_scores(id_theme, id_player, score) VALUES(?, ?, 0)";
        
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            try (PreparedStatement psGetThemes = conn.prepareStatement("SELECT id_theme FROM themes");
                 ResultSet themeRs = psGetThemes.executeQuery()) {
                
                while (themeRs.next()) {
                    int themeId = themeRs.getInt("id_theme");
                    stmt.setInt(1, themeId);
                    stmt.setInt(2, playerId);
                    stmt.executeUpdate();
                }
            }
        }
    }
}