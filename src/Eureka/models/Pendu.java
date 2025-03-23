package Eureka.models;

public class Pendu {
    private static int maxAttempts = 8; // nbr max de tentatives
    int attemptsLeft;
    

    public Pendu() {
        this.attemptsLeft = maxAttempts;
    }
    
    public int getAttemptsLeft() { 
        return attemptsLeft; 
    }

    public void setAttemptsLeft(int attemptsLeft) { 
        this.attemptsLeft = attemptsLeft; 
    }

    public boolean isGameOver() { 
        return attemptsLeft <= 0; 
    }

 }
