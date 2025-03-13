package Eureka.models;

public class Badge {
    int badge_id;
    String name; // nom du badge
    String description; // description du badge
    int requiredAchievements;
    String theme;


    public Badge(String name, String description, int requiredAchievements, String theme) {
        this.name = name;
        this.description = description;
        this.requiredAchievements = requiredAchievements;
        this.theme = theme;
    }
    
    public String getName() { 
        return name; 
    }

    public String getDescription() { 
        return description; 
    }

    public int getRequiredAchievements() { 
        return requiredAchievements; 
    }

    public String getTheme() { 
        return theme; 
    }

    public int getBadge_id() {
        return badge_id;
    }

    public void setBadge_id(int badge_id) {
        this.badge_id = badge_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequiredAchievements(int requiredAchievements) {
        this.requiredAchievements = requiredAchievements;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    
}
