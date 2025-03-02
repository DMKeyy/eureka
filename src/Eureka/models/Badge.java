package Eureka.models;

public class Badge {
    String name; // nom du badge
    String description; // description du badge
    int requiredAchievements; // nbr de succès qu'il faut pour débloquer
    String theme1; 
    String theme2; 
    String theme3;
    String theme4; 


    public Badge(String name, String description, int requiredAchievements, String theme1, String theme2, String theme3, String theme4) {
        this.name = name;
        this.description = description;
        this.requiredAchievements = requiredAchievements;
        this.theme1 = theme1;
        this.theme2 = theme2;
        this.theme3 = theme3;
        this.theme4 = theme4;
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

    public String getTheme1() { 
        return theme1; 
    }

    public String getTheme2() {
        return theme2; 
    }

    public String getTheme3() { 
        return theme3; 
    }

    public String getTheme4() { 
        return theme4; 
    }

}
