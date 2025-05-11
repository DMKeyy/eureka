package Eureka.models.GameModeRep;

public class GameMode {
    private int modeId;
    private String name;

    public GameMode(int modeId, String name) {
        this.modeId = modeId;
        this.name = name;
    }

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}