package Eureka.models;

import java.time.LocalDate;
import java.util.List;

public class Player{

    private String username;
    private String password;
    private LocalDate registrationDate;
    private int score; // score 
    private int dailyChallengesCompleted; // nbr de defis quotidiens terminés
    private int BestScore;
    private int totalGamesPlayed; // nbr total de parties jouées
    private List<Badge> badges; // liste des badges obtenus
    private int streakCount; // nbr de reponses consecutives correctes
    private int longestCompletionTime; // temps le plus long pour terminer un défi
    private int correctAnswersTheme1; // compteur de réponses correctes pour le thème 1
    private int correctAnswersTheme2; 
    private int correctAnswersTheme3; 
    private int correctAnswersTheme4;

 
    
    public Player(String username, String password,LocalDate registrationDate) {
        this.username = username;
        this.password = password;
        this.registrationDate = LocalDate.now(); 
        this.score = 0;
        this.dailyChallengesCompleted = 0;
        this.totalGamesPlayed = 0;
        this.streakCount = 0;
        this.longestCompletionTime = Integer.MAX_VALUE;
        this.correctAnswersTheme1 = 0;
        this.correctAnswersTheme2 = 0;
        this.correctAnswersTheme3 = 0;
        this.correctAnswersTheme4 = 0;

    }
    
    public String getUsername() {
        return username;   
    }

    public String getPassword() { 
        return password; 
    }



    public LocalDate getRegistrationDate() { 
      return registrationDate; 
    }
    
    public int getBestScore() { 
        return BestScore; 
    }

    public void setBestScore(int BestScore) { 
        this.BestScore = BestScore; 
    }

    public int getScore() { 
        return score; 
    }

    public void addScore(int points) { 
        this.score += points; 
    }


    public int getDailyChallengesCompleted() { 
        return dailyChallengesCompleted; 
    }

    public void completeDailyChallenge() { 
        this.dailyChallengesCompleted++; 
    }

    public int getTotalGamesPlayed() { 
        return totalGamesPlayed; 
    }

    public void incrementGamesPlayed() { 
        this.totalGamesPlayed++; 
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }
    
    public List<Badge> getBadges() { 
        return badges; 
}

    public int getStreakCount() { 
        return streakCount; 
    }

    public void incrementStreak() { 
        this.streakCount++; 
    }

    public void resetStreak() { 
        this.streakCount = 0; 
    }

    public int getLongestCompletionTime() { 
        return longestCompletionTime; 
    }

    public void updateLongestCompletionTime(int time) {
        if (time < this.longestCompletionTime) {
            this.longestCompletionTime = time;
        }
    }

    public int getCorrectAnswersTheme1() { 
        return correctAnswersTheme1; 
    }

    public void incrementCorrectAnswersTheme1() { 
        this.correctAnswersTheme1++; 
    }

    public int getCorrectAnswersTheme2() { 
        return correctAnswersTheme2; 
    }

    public void incrementCorrectAnswersTheme2() { 
        this.correctAnswersTheme2++; 
    }

    public int getCorrectAnswersTheme3() { 
        return correctAnswersTheme3; 
   
    }
    public void incrementCorrectAnswersTheme3() { 
        this.correctAnswersTheme3++; 
    }

    public int getCorrectAnswersTheme4() { 
        return correctAnswersTheme4; 
    }

    public void incrementCorrectAnswersTheme4() { 
        this.correctAnswersTheme4++; 
    }

}
