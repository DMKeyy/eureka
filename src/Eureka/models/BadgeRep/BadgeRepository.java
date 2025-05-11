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
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int required = rs.getInt("required_correct_answers");
                String theme = rs.getString("theme");
                String rarityString = rs.getString("rarity"); 
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
        String query = "SELECT * FROM player_badges WHERE player_id = ? AND badge_id = ?";
        try (Connection conn = DatabaseService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, player.getPlayerId());
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
        String query = "INSERT IGNORE INTO player_badges (player_id, badge_id) VALUES (?, ?)";

        try (Connection conn = DatabaseService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, player.getPlayerId());
            stmt.setInt(2, badgeId);
            stmt.executeUpdate();
            System.out.println("Badge assigned to player: " + player.getUsername() + " - Badge ID: " + badgeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Badge> getPlayerBadges(int playerId) {
        List<Badge> badges = new ArrayList<>(); 
        String query = "SELECT b.* FROM badges b JOIN player_badges pb ON b.id = pb.badge_id WHERE pb.player_id = ?";

        try (Connection conn = DatabaseService.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Badge badge = new Badge(
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("required_correct_answers"),
                    rs.getString("theme"),
                    Badge.BadgeRarity.valueOf(rs.getString("rarity").toUpperCase())
                );
                badge.setBadge_id(rs.getInt("id"));
                badges.add(badge);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return badges;
    }
}