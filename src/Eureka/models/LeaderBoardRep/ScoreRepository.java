package Eureka.models.LeaderBoardRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Eureka.models.DatabaseService;
import Eureka.models.GameModeRep.GameMode;
import Eureka.models.PlayerRep.Player;

public class ScoreRepository {
    
    public static void updateScore(Player player, GameMode mode, int newScore) {

        int currentScore = getScore(player, mode);
        

        if (newScore > currentScore) {
            String query = "UPDATE scores SET score = ? WHERE id_player = ? AND id_mode = ?";
            
            try (Connection conn = DatabaseService.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setInt(1, newScore);
                stmt.setInt(2, player.getPlayerId());
                stmt.setInt(3, mode.getModeId());
                stmt.executeUpdate();

                player.addScore(mode, newScore);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getScore(Player player, GameMode mode) {
        String query = "SELECT score FROM scores WHERE id_player = ? AND id_mode = ?";
        
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, player.getPlayerId());
            stmt.setInt(2, mode.getModeId());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void initializePlayerScores(int playerId) throws SQLException {
        String query = "INSERT INTO scores(id_mode, id_player, score) VALUES(?, ?, 0)";
        
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            try (PreparedStatement psGetModes = conn.prepareStatement("SELECT id_mode FROM modes WHERE nom != 'Multi'");
                 ResultSet modeRs = psGetModes.executeQuery()) {
                
                while (modeRs.next()) {
                    int modeId = modeRs.getInt("id_mode");
                    stmt.setInt(1, modeId);
                    stmt.setInt(2, playerId);
                    stmt.executeUpdate();
                }
            }
        }
    }

    public static void resetScore(Player player, GameMode mode) {
        updateScore(player, mode, 0);
    }
}