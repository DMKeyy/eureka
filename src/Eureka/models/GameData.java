package Eureka.models;

public class GameData {

    private static String theme;
    private static int difficulty;

    public static void setTheme(String t) { theme = t; }
    public static String getTheme() { return theme; }
    
    public static void setDifficulty(int d) { difficulty = d; }
    public static int getDifficulty() { return difficulty; }

    
}
