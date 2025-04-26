package Eureka.models;

public class GameData {

    private static String theme;
    private static int difficulty;
    private static String mode;
    private static int score;

    public static void setTheme(String t) { theme = t; }
    public static String getTheme() { return theme; }
    
    public static void setDifficulty(int d) { difficulty = d; }
    public static int getDifficulty() { return difficulty; }

    public static void setMode(String selectedMode) {mode = selectedMode;}
    public static String getMode() {return mode;}

    public static void setScore(int s) { score = s; }
    public static int getScore() { return score; }
}

