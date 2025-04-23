package Eureka.models.LeaderBoardRep;

public class LeaderboardEntry {

    // bedelha kima thb jsp ida util wla lala
    private String username;
    private int score;
    private int correctAnswers;

    public LeaderboardEntry(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public LeaderboardEntry(String username, int score, int correctAnswers) {
        this.username = username;
        this.score = score;
        this.correctAnswers = correctAnswers;
    }

    
    public String getUsername() { return username; }
    public int getScore() { return score; }
    public int getCorrectAnswers() { return correctAnswers; }
}