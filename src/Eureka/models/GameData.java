package Eureka.models;


import Eureka.models.GameModeRep.GameMode;
import Eureka.models.ThemeRep.Theme;

public class GameData {

    private static Theme theme;
    private static int difficulty;
    private static GameMode mode;
    private static int score;

    
    public static Theme getTheme() {
        return theme;
    }
    public static void setTheme(Theme theme) {
        GameData.theme = theme;
    }
    public static int getDifficulty() {
        return difficulty;
    }
    public static void setDifficulty(int difficulty) {
        GameData.difficulty = difficulty;
    }
    public static GameMode getMode() {
        return mode;
    }
    public static void setMode(GameMode mode) {
        GameData.mode = mode;
    }
    public static int getScore() {
        return score;
    }
    public static void setScore(int score) {
        GameData.score = score;
    }


}

