package Eureka.models.BadgeRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Eureka.models.DatabaseService;
import Eureka.models.PlayerRep.Player;

public class BadgeRepository {
    
    public static List<Badge> getAllBadges() {
        List<Badge> badges = new ArrayList<>();
        String query = "SELECT * FROM badges";

        try (Connection conn = DatabaseService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID_badge");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int required = rs.getInt("Required_correct_answers");
                String theme = rs.getString("Theme");
                String rarityString = rs.getString("Rarity"); 
                Badge.BadgeRarity rarity = Badge.BadgeRarity.valueOf(rarityString.toUpperCase(Locale.ROOT));
                
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
        try (Connection conn = DatabaseService.getConnection();
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

        try (Connection conn = DatabaseService.getConnection();
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

        try (Connection conn = DatabaseService.getConnection();
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
        try (Connection conn = DatabaseService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, count);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}