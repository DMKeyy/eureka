package Eureka.models;

import java.util.List;

public class Player extends User {
    private int score; // score 
    private int dailyChallengesCompleted; // nbr de defis quotidiens terminés
    private int totalGamesPlayed; // nbr total de parties jouées
    private List<Badge> badges; // liste des badges obtenus
    private int bonusTime; // bonus gagné
    private int achievementsUnlocked; // nbr de succès débloqués
    private int streakCount; // nbr de reponses consecutives correctes
    private int longestCompletionTime; // temps le plus long pour terminer un défi
    private int correctAnswersTheme1; // compteur de réponses correctes pour le thème 1
    private int correctAnswersTheme2; 
    private int correctAnswersTheme3; 
    private int correctAnswersTheme4;
    private int totalCorrectAnswers; // nbr total de reponses correctes
    //private int level; 
    //private int xpPoints;
    // private int totalWins; // nbr total de victoires
    
    
    public Player(String username, String password) {
        super(username, password);
        this.score = 0;
        this.dailyChallengesCompleted = 0;
        this.totalGamesPlayed = 0;
        this.bonusTime = 0;
        this.achievementsUnlocked = 0;
        this.streakCount = 0;
        this.longestCompletionTime = Integer.MAX_VALUE;
        this.correctAnswersTheme1 = 0;
        this.correctAnswersTheme2 = 0;
        this.correctAnswersTheme3 = 0;
        this.correctAnswersTheme4 = 0;
        this.totalCorrectAnswers = 0;
        //this.level = 1;
        //this.xpPoints = 0;
        //this.totalWins = 0;

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

    public int getBonusTime() { 
        return bonusTime; 
    }

    public void addBonusTime(int seconds) { 
        this.bonusTime += seconds; 
    }

    public int getAchievementsUnlocked() { 
        return achievementsUnlocked; 
    }

    public void unlockAchievement() { 
        this.achievementsUnlocked++; 
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
    public int getTotalCorrectAnswers() { 
        return totalCorrectAnswers; 
    }

    private void incrementTotalCorrectAnswers() { 
        this.totalCorrectAnswers++; 
    }
}

// public int getLevel() { 
     //   return level; 
   // }

    //public void levelUp() { 
       // this.level++; 
    //}

    //public int getXpPoints() { 
      //  return xpPoints; 
    //}

   // public void addXpPoints(int points) {
     //   this.xpPoints += points;
       // if (this.xpPoints >= 200) {  
         //   levelUp();
           // this.xpPoints = 0;
        //}
    //}

     //public int getTotalWins() { 
      //  return totalWins; 
    //}

   // public void incrementWins() { 
     //   this.totalWins++; 
    //}
