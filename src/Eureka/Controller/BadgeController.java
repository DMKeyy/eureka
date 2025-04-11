package Eureka.Controller;

import Eureka.models.Badge;
import Eureka.models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;

public class BadgeController {

    @FXML
    private Button btn_back;
    
    @FXML
    private ListView<String> badgeListView;

    @FXML
    public void initialize() {
        Player player = Player.getCurrentPlayer();
        List<Badge> badges = DbController.getPlayerBadges(player.getUsername());

        btn_back.setOnAction(e -> {
            SoundEffects.clickSound.play();
            DbController.changeScene(e, "Profile.fxml");
        });

        for (Badge badge : badges) {
            String info = String.format("🏅 %s\n• %s\n• Thème : %s  |  Rareté : %s\n",
                    badge.getName(),
                    badge.getDescription(),
                    badge.getTheme(),
                    badge.getRarity().toString());

            badgeListView.getItems().add(info);
        }
    }

   

    public static void checkAndAssignBadges(Player player) {
        List<Badge> allBadges = DbController.getAllBadges();
        boolean newBadgeAssigned = false;
    
        for (Badge badge : allBadges) {
            boolean alreadyHas = DbController.playerHasBadge(player, badge);
    
            if (!alreadyHas && meetsCriteria(player, badge)) {
                DbController.assignBadgeToPlayer(player, badge.getBadge_id());
                System.out.println("🎖 Badge attribué : " + badge.getName());
                newBadgeAssigned = true;
            }
        }
    
        if (newBadgeAssigned) {
            int updatedCount = DbController.getPlayerBadges(player.getUsername()).size();
            player.setBadgeCount(updatedCount);
            DbController.updateBadgeCount(player.getUsername(), updatedCount);
        }
    }
    

    //Vérifie si le joueur remplit les conditions d'obtention du badge donné
    private static boolean meetsCriteria(Player player, Badge badge) {
        String theme = badge.getTheme().toLowerCase();
        int required = badge.getRequiredAchievements();
        String badgeName = badge.getName().toLowerCase();

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
             case "secure" -> player.getPassword().length() >= required;
             case "username" -> {
                // On regarde le nom du badge pour faire la différence entre "lazy" et "developer"
                if (badgeName.contains("lazy")) {
                    yield player.getUsername().length() == 1 && player.getPassword().length() == 1;
    
                } else if (badgeName.contains("developer")) {
                    // Condition : username ∈ {kheiro, anis, aya, ilyes} (insensible à la casse)
                    String lowerUser = player.getUsername().toLowerCase();
                    yield lowerUser.equals("kheiro") 
                        || lowerUser.equals("anis") 
                        || lowerUser.equals("aya") 
                        || lowerUser.equals("ilyes");
    
                } else {
                    yield false;
                }
            }

        default -> false;
    };
    }
    

}