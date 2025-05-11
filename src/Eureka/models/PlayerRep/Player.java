package Eureka.models.PlayerRep;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Eureka.models.BadgeRep.Badge;
import Eureka.models.GameModeRep.GameMode;
import Eureka.models.ThemeRep.Theme;

public class Player {
    private static Player currentPlayer;
    
    private int player_id;
    private String username;
    private String password;
    private LocalDate registrationDate;
    private int score; // score 
    private int BestScore;
    private int BestSurvivalScore;
    private int BestTimeTrialScore;
    private int BestProgressiveTimeTrialScore;
    private int BestMissingLetterScore;
    private int BestMcqScore;
    private int totalGamesPlayed;
    private List<Badge> badges;
    private int streakCount;
    private int correctAnswersScience;
    private int correctAnswersHistory; 
    private int correctAnswersGeography; 
    private int correctAnswersSport;
    private int correctAnswersArt;
    private int correctAnswersJava;
    private int correctAnswersIslam;
    private int badgeCount;

    public Player(String username, String password, LocalDate registrationDate) {
        this.player_id = -1; // Will be updated after database insert
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
        this.score = 0;
        this.badges = new ArrayList<>();
        this.BestScore = 0;
        this.BestSurvivalScore = 0;
        this.BestTimeTrialScore = 0;
        this.BestProgressiveTimeTrialScore = 0;
        this.BestMissingLetterScore = 0;
        this.BestMcqScore = 0;
        this.badgeCount = 0;
        this.totalGamesPlayed = 0;
        this.streakCount = 0;
        this.correctAnswersScience = 0;
        this.correctAnswersHistory = 0;
        this.correctAnswersGeography = 0;
        this.correctAnswersSport = 0;
        this.correctAnswersArt = 0;
        this.correctAnswersJava = 0;
        this.correctAnswersIslam = 0;
    }

    
    
    
    public Player(int player_id, String username, String password, LocalDate registrationDate, int score, int bestScore, int BestSurvivalScore, int bestTimeTrialScore,  int bestProgressiveTimeTrialScore, int MissingLetterScore, int BestMcqScore, int totalGamesPlayed, int streakCount, int correctAnswersScience, int correctAnswersHistory, int correctAnswersGeography, int correctAnswersSport, int correctAnswersArt, int correctAnswersJava, int correctAnswersIslam, int badgeCount) {
        this.player_id = player_id;
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
        this.score = score;
        BestScore = bestScore;
        this.BestSurvivalScore = BestSurvivalScore;
        this.BestTimeTrialScore = bestTimeTrialScore;
        this.BestProgressiveTimeTrialScore = bestProgressiveTimeTrialScore;
        this.BestMissingLetterScore = MissingLetterScore;
        this.totalGamesPlayed = totalGamesPlayed;
        this.streakCount = streakCount;
        this.correctAnswersScience = correctAnswersScience;
        this.correctAnswersHistory = correctAnswersHistory;
        this.correctAnswersGeography = correctAnswersGeography;
        this.correctAnswersSport = correctAnswersSport;
        this.correctAnswersArt = correctAnswersArt;
        this.correctAnswersJava = correctAnswersJava;
        this.correctAnswersIslam = correctAnswersIslam;
        this.badgeCount = badgeCount;

    }

    public Player(int playerId, String username, String password, LocalDate registrationTime, int totalGamesPlayed, int streakCount, int badgeCount2) {
       player_id = playerId;
        this.username = username;
        this.password = password;
        this.registrationDate = registrationTime;
        this.totalGamesPlayed = totalGamesPlayed;
        this.streakCount = streakCount;
        this.badgeCount = badgeCount2;
        this.score = 0;
        this.badges = new ArrayList<>();
        this.BestScore = 0;
        this.BestSurvivalScore = 0;
        this.BestTimeTrialScore = 0;
        this.BestProgressiveTimeTrialScore = 0;
        this.BestMissingLetterScore = 0;
        this.BestMcqScore = 0;
        this.badgeCount = 0;
        this.totalGamesPlayed = 0;
        this.streakCount = 0;
        this.correctAnswersScience = 0;
        this.correctAnswersHistory = 0;
        this.correctAnswersGeography = 0;
        this.correctAnswersSport = 0;
        this.correctAnswersArt = 0;
        this.correctAnswersJava = 0;
        this.correctAnswersIslam = 0;
    }


    public int getPlayerId() {
        return player_id;
    }

    public void setPlayerId(int player_id) {
        this.player_id = player_id;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Player.currentPlayer = currentPlayer;
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

    public int getBestSurvivalScore() { 
        return BestSurvivalScore; 
    }
    public void setBestSurvivalScore(int BestSurvivalScore) { 
        this.BestSurvivalScore = BestSurvivalScore; 
    }

    public void addScore(int points) { 
        this.score += points; 
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

    public int getCorrectAnswersScience() { 
        return correctAnswersScience; 
    }

    public int getCorrectAnswersHistory() { 
        return correctAnswersHistory; 
    }

    public int getCorrectAnswersGeography() { 
        return correctAnswersGeography; 
    }

    public int getCorrectAnswersSport() { 
        return correctAnswersSport; 
    }

    public int getCorrectAnswersArt() { 
        return correctAnswersArt; 
    }

    public int getCorrectAnswersJava() { 
        return correctAnswersJava; 
    }

    public int getCorrectAnswersIslam() { 
        return correctAnswersIslam; 
    }
    

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setBestTimeTrialScore(int bestScore) {
        BestTimeTrialScore = bestScore;
    }

    public int getBestTimeTrialScore() {
        return BestTimeTrialScore;  
    }

    public void setBestProgressiveTimeTrialScore(int bestProgressiveTimeTrialScore) {
        BestProgressiveTimeTrialScore = bestProgressiveTimeTrialScore;
    }

    public int getBestProgressiveTimeTrialScore() {
        return BestProgressiveTimeTrialScore;  
    }

    public  void setBestMissingLetterScore(int bestScore) {
        BestMissingLetterScore = bestScore;
    }

    public int getBestMissingLetterScore() {
        return BestMissingLetterScore;  
    }

    public int getBestMcqScore() { 
        return BestMcqScore; 
    }
    public void setBestMcqScore(int BestMcqScore) { 
        this.BestMcqScore = BestMcqScore; 
    }

    public void setCorrectAnswersScience(int correctAnswersScience) {
        this.correctAnswersScience = correctAnswersScience;
    }

    public void setCorrectAnswersHistory(int correctAnswersHistory) {
        this.correctAnswersHistory = correctAnswersHistory;
    }

    public void setCorrectAnswersGeography(int correctAnswersGeography) {
        this.correctAnswersGeography = correctAnswersGeography;
    }

    public void setCorrectAnswersSport(int correctAnswersSport) {
        this.correctAnswersSport = correctAnswersSport;
    }

    public void setCorrectAnswersArt(int correctAnswersArt) {
        this.correctAnswersArt = correctAnswersArt;
    }

    public void setCorrectAnswersJava(int correctAnswersJava) {
        this.correctAnswersJava = correctAnswersJava;
    }

    public void setCorrectAnswersIslam(int correctAnswersIslam) {
        this.correctAnswersIslam = correctAnswersIslam;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }
    
    public void setStreakCount(int streakCount) {
        this.streakCount = streakCount;
    }

    public int getBadgeCount() {
        return badgeCount;
    }
    public void setBadgeCount(int badgeCount) {
        this.badgeCount = badgeCount;
    }


    public void addScore(GameMode mode, int score) {

        switch (mode.getName()) {
            case "Survival":
                this.BestSurvivalScore = score;
                break;
            case "TimeTrial":
                this.BestTimeTrialScore = score;
                break;
            case "ProgressiveTimeTrial":
                this.BestProgressiveTimeTrialScore = score;
                break;
            case "MissingLetters":
                this.BestMissingLetterScore = score;
                break;
            case "Mcq":
                this.BestMcqScore = score;
                break;
            case "Basic":
                this.BestScore = score;
                break;
            default:
                System.out.println("Unknown game mode: " + mode.getName());
                break;
        }
    }




    public void addThemeStats(Theme theme, int correctAnswers) {
        switch (theme.getName()) {
            case "Science":
                this.correctAnswersScience += correctAnswers;
                break;
            case "History":
                this.correctAnswersHistory += correctAnswers;
                break;
            case "Geography":
                this.correctAnswersGeography += correctAnswers;
                break;
            case "Sport":
                this.correctAnswersSport += correctAnswers;
                break;
            case "Art":
                this.correctAnswersArt += correctAnswers;
                break;
            case "Java":
                this.correctAnswersJava += correctAnswers;
                break;
            case "Islam":
                this.correctAnswersIslam += correctAnswers;
                break;
            default:
                System.out.println("Unknown theme: " + theme.getName());
                break;
        }
    }




    @Override
    public String toString() {
        return "Player [player_id=" + player_id + ", username=" + username + ", password=" + password
                + ", registrationDate=" + registrationDate + ", score=" + score + ", BestScore=" + BestScore
                + ", BestSurvivalScore=" + BestSurvivalScore + ", BestTimeTrialScore=" + BestTimeTrialScore
                + ", BestProgressiveTimeTrialScore=" + BestProgressiveTimeTrialScore + ", BestMissingLetterScore="
                + BestMissingLetterScore + ", BestMcqScore=" + BestMcqScore + ", totalGamesPlayed=" + totalGamesPlayed
                + ", badges=" + badges + ", streakCount=" + streakCount + ", correctAnswersScience="
                + correctAnswersScience + ", correctAnswersHistory=" + correctAnswersHistory
                + ", correctAnswersGeography=" + correctAnswersGeography + ", correctAnswersSport="
                + correctAnswersSport + ", correctAnswersArt=" + correctAnswersArt + ", correctAnswersJava="
                + correctAnswersJava + ", correctAnswersIslam=" + correctAnswersIslam + ", badgeCount=" + badgeCount
                + "]";
    }

    
}
