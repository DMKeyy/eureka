package Eureka.models.BadgeRep;

import java.util.ArrayList;
import java.util.List;

import Eureka.models.PlayerRep.Player;

public class Badge {
    int badge_id;
    String name;
    String description;
    int requiredAchievements;
    String theme;
    public enum BadgeRarity {
        COMMON,
        RARE,
        EPIC,
        LEGENDARY
    }
    BadgeRarity rarity;


    public Badge(String name, String description, int requiredAchievements, String theme, BadgeRarity rarity) {
        this.badge_id = 0; // default value, can be set later
        this.name = name;
        this.description = description;
        this.requiredAchievements = requiredAchievements;
        this.theme = theme;
        this.rarity = rarity;
    }
    
    public String getName() { 
        return name; 
    }

    public String getDescription() { 
        return description; 
    }

    public int getRequiredAchievements() { 
        return requiredAchievements; 
    }

    public String getTheme() { 
        return theme; 
    }

    public int getBadge_id() {
        return badge_id;
    }

    public void setBadge_id(int badge_id) {
        this.badge_id = badge_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequiredAchievements(int requiredAchievements) {
        this.requiredAchievements = requiredAchievements;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
    public BadgeRarity getRarity() {
        return rarity;
    }
    public void setRarity(BadgeRarity rarity) {
        this.rarity = rarity;
    }

    //evite les doublons
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Badge other = (Badge) obj;
        return badge_id == other.badge_id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(badge_id);
    }

    @Override
    public String toString() {
        return String.format("Badge[%d] %s (%s)", badge_id, name, rarity);
    }

    public static boolean meetsCriteria(Player player, Badge badge) {
        String theme = badge.getTheme().toLowerCase();
        int required = badge.getRequiredAchievements();

        return switch (theme) {
            case "science" -> player.getCorrectAnswersScience() >= required;
            case "history" -> player.getCorrectAnswersHistory() >= required;
            case "geography" -> player.getCorrectAnswersGeography() >= required;
            case "art" -> player.getCorrectAnswersArt() >= required;
            case "islam" -> player.getCorrectAnswersIslam() >= required;
            case "java" -> player.getCorrectAnswersJava() >= required;
            case "sport" -> player.getCorrectAnswersSport() >= required;
            case "all" -> player.getTotalGamesPlayed() >= required; // badge global
            case "survival" -> player.getBestSurvivalScore() >= required;
            case "veteran" -> {
            // Joue depuis X jours ?
            long days = java.time.temporal.ChronoUnit.DAYS.between(
                    player.getRegistrationDate(), java.time.LocalDate.now());
            yield days >= required;
        }

        default -> false;
    };
    }

    public static List<Badge> checkAndAssignBadges(Player player) {
        List<Badge> allBadges = BadgeRepository.getAllBadges();
        List<Badge> earned = new ArrayList<>(); 

        for (Badge badge : allBadges) {
            boolean alreadyHas = BadgeRepository.playerHasBadge(player, badge);

            if (!alreadyHas && meetsCriteria(player, badge)) {
                BadgeRepository.assignBadgeToPlayer(player, badge.getBadge_id());
                System.out.println("🎖 Badge attribué : " + badge.getName());
                earned.add(badge); 
            }
        }

        if (!earned.isEmpty()) {
            int updatedCount = BadgeRepository.getPlayerBadges(player.getPlayerId()).size();
            player.setBadgeCount(updatedCount);
        }

        return earned;
    }

    
}