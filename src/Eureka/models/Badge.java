package Eureka.models;

public class Badge {
    int badge_id;
    String name; // nom du badge
    String description; // description du badge
    int requiredAchievements;
    String theme;
    public enum BadgeRarity {
        COMMON,
        RARE,
        EPIC,
        LEGENDARY
    }
    BadgeRarity rarity;   //la rareté du badge wsh le copilot yi9dar yidri tanik les commentaite haha haha daro wa7do hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh 


    public Badge(String name, String description, int requiredAchievements, String theme, BadgeRarity rarity) {
        this.badge_id = 0; // default value, can be set later
        this.name = name;
        this.description = description;
        this.requiredAchievements = requiredAchievements;
        this.theme = theme;
        this.rarity = rarity;
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
    public BadgeRarity getRarity() {
        return rarity;
    }
    public void setRarity(BadgeRarity rarity) {
        this.rarity = rarity;
    }

    //evite les doublons
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Badge other = (Badge) obj;
        return badge_id == other.badge_id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(badge_id);
    }

    @Override
    public String toString() {
        return String.format("Badge[%d] %s (%s)", badge_id, name, rarity);
    }

    
}