package Eureka.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Eureka.models.Badge;
import Eureka.models.Player;
import Eureka.models.SoundEffects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button; 
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameOverController {

    @FXML
    private AnchorPane root;
    @FXML
    private Button btn_again, btn_leave, btn_revision;
    @FXML 
    private Label lbl_score;
    @FXML
    private Label lbl_message;

    public void initialize() {
        
        SoundEffects.addSound(btn_again);
        SoundEffects.addSound(btn_leave);
        SoundEffects.addSound(btn_revision);

        List<Badge>Badgeswon = checkAndAssignBadges(Player.getCurrentPlayer());
        if (!Badgeswon.isEmpty()) {
            StringBuilder message = new StringBuilder("Bravo ! Vous avez gagné les badges suivants :\n");
            for (Badge badge : Badgeswon) {
                message.append(badge.getName()).append("\n");
            }
            lbl_message.setText(message.toString());
        }

        btn_again.setOnAction(e -> {
            try {
            AnchorPane settings = FXMLLoader.load(getClass().getResource("/Eureka/View/fxml/ThemeChooser.fxml"));
            settings.setLayoutX((root.getWidth() - settings.getPrefWidth()) / 2);
            settings.setLayoutY((root.getHeight() - settings.getPrefHeight()) / 2);
            root.getChildren().add(settings);
            settings.requestFocus();

            } catch (IOException ex) {
            ex.printStackTrace();
        }
        });

        btn_leave.setOnAction(e -> {
            DbController.changeScene(e, "ChoseGameMode.fxml");
        });

        btn_revision.setOnAction(e -> {
            DbController.changeScene(e, "ModeRevision.fxml");
        });

        
    }

    public void setScore(int score) {
        lbl_score.setText(String.valueOf(score));
    }

    public void setGameOverMessage(String message) {
        if (lbl_message != null) {
            lbl_message.setText(message);
        }
    }

    public static List<Badge> checkAndAssignBadges(Player player) {
        List<Badge> allBadges = DbController.getAllBadges();
        List<Badge> earned = new ArrayList<>(); 

        for (Badge badge : allBadges) {
            boolean alreadyHas = DbController.playerHasBadge(player, badge);

            if (!alreadyHas && meetsCriteria(player, badge)) {
                DbController.assignBadgeToPlayer(player, badge.getBadge_id());
                System.out.println("🎖 Badge attribué : " + badge.getName());
                earned.add(badge); 
            }
        }

        if (!earned.isEmpty()) {
            int updatedCount = DbController.getPlayerBadges(player.getUsername()).size();
            player.setBadgeCount(updatedCount);
            DbController.updateBadgeCount(player.getUsername(), updatedCount);
        }

        return earned;
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
