package Eureka.models.ThemeRep;

public class Theme {
    private int themeId;
    private String name;

    public Theme(int themeId, String name) {
        this.themeId = themeId;
        this.name = name;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
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