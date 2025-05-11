package Eureka.models.LeaderBoardRep;

public class ScoreEntry {
    private String mode;
    private int score;

    public ScoreEntry(String mode, int score) {
        this.mode = mode;
        this.score = score;
    }
    public String getMode() { return mode; }
    public int getScore() { return score; }
}
