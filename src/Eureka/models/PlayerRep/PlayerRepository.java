package Eureka.models.PlayerRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import Eureka.models.DatabaseService;
import Eureka.models.PasswordHasher;
import Eureka.models.BadgeRep.BadgeRepository;
import Eureka.models.GameModeRep.GameMode;
import Eureka.models.GameModeRep.GameModeRepository;
import Eureka.models.ThemeRep.Theme;
import Eureka.models.ThemeRep.ThemeRepository;
import Eureka.models.LeaderBoardRep.ScoreRepository;

public class PlayerRepository {
    
    public static boolean isUsernameValid(String username) {
        String query = "SELECT username FROM player WHERE username = ?";
        
        try (Connection conn = DatabaseService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void signUpUser(String username, String password) throws SQLException {
        String query = "INSERT INTO player (username, password, registration_time) VALUES (?, ?, ?)";
        LocalDate registrationTime = LocalDate.now();

        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement psInsertUser = connection.prepareStatement(query)) {
            
            String hashedPassword = PasswordHasher.hashPassword(password);
            psInsertUser.setString(1, username);
            psInsertUser.setString(2, hashedPassword);
            psInsertUser.setDate(3, java.sql.Date.valueOf(registrationTime));
            psInsertUser.executeUpdate();
            
            int playerId = getPlayerIdByUsername(username);
            Player.setCurrentPlayer(new Player(playerId, username, hashedPassword, registrationTime, 0, 0, 0));
            if (playerId == -1) {
                throw new SQLException("Failed to retrieve player ID after sign-up.");
            }
            ScoreRepository.initializePlayerScores(playerId);
            ThemeRepository.initializePlayerThemeStats(playerId);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new SQLException("Error hashing password: " + e.getMessage(), e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static int getPlayerIdByUsername(String username) {
        String query = "SELECT player_id FROM player WHERE username = ?";
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("player_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Player loginUser(String username, String password) throws SQLException {
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement psCheckUserExist = connection.prepareStatement("SELECT * FROM player WHERE Username = ?")) {

            psCheckUserExist.setString(1, username);
            try (ResultSet rs = psCheckUserExist.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("Password");
                    try {
                        if (PasswordHasher.verifyPassword(password, storedHash)) {
                            Player player = loadPlayerData(rs);
                            loadPlayerScores(player);
                            loadPlayerThemeStats(player);
                            return player;
                        }
                    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                        throw new SQLException("Error verifying password: " + e.getMessage(), e);
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

            String hashedPassword = PasswordHasher.hashPassword(newPassword);
            stmt.setString(1, hashedPassword);
            stmt.setString(2, username);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUsername(Player player, String newUsername) {
        String query = "UPDATE player SET username = ? WHERE player_id = ?";
    
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newUsername);
            stmt.setInt(2, player.getPlayerId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void updatePlayer(Player player) {
        String query = "UPDATE player SET total_game_played = ?, streak_count = ? WHERE player_id = ?";
    
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            pstmt.setInt(1, player.getTotalGamesPlayed());
            pstmt.setInt(2, player.getStreakCount());
            pstmt.setInt(3, player.getPlayerId());
    
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Player loadPlayerData(ResultSet rs) throws SQLException {
        int playerId = rs.getInt("player_id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        LocalDate registrationTime = rs.getDate("registration_time").toLocalDate();
        int totalGamesPlayed = rs.getInt("total_game_played");
        int streakCount = rs.getInt("streak_count");
        int badgeCount = BadgeRepository.getPlayerBadges(playerId).size();

        return new Player(playerId, username, password, registrationTime, totalGamesPlayed, streakCount, badgeCount);
    }

    private static void loadPlayerScores(Player player) throws SQLException {
        List<GameMode> gameModes = GameModeRepository.getAllGameModes();
        for (GameMode mode : gameModes) {
            int score = ScoreRepository.getScore(player, mode);
            player.addScore(mode, score);
        }
    }

    private static void loadPlayerThemeStats(Player player) throws SQLException {
        String query = "SELECT id_theme, score FROM correct_answers_scores WHERE id_player = ?";
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, player.getPlayerId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int themeId = rs.getInt("id_theme");
                int correctAnswers = rs.getInt("score");
                Theme theme = ThemeRepository.getThemeById(themeId);
                if (theme != null) {
                    player.addThemeStats(theme, correctAnswers);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}