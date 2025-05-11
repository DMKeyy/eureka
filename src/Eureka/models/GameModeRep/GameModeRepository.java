package Eureka.models.GameModeRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Eureka.models.DatabaseService;

public class GameModeRepository {
    
    public static List<GameMode> getAllGameModes() {
        List<GameMode> modes = new ArrayList<>();
        String query = "SELECT id_mode, nom FROM modes";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                GameMode mode = new GameMode(
                    rs.getInt("id_mode"),
                    rs.getString("nom")
                );
                modes.add(mode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modes;
    }

    public static GameMode getGameModeById(int modeId) {
        String query = "SELECT id_mode, nom FROM modes WHERE id_mode = ?";

        try (Connection conn = DatabaseService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, modeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new GameMode(rs.getInt("id_mode"), rs.getString("nom"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GameMode getGameModeByName(String name) {
        String query = "SELECT id_mode, nom FROM modes WHERE nom = ?";

        try (Connection conn = DatabaseService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new GameMode(rs.getInt("id_mode"), rs.getString("nom"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}