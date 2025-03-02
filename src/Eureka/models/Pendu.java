package Eureka.models;

public class Pendu {
    int maxAttempts = 8; // nbr max de tentatives
    int attemptsLeft; // nbr de tentatives restantes
    

    public Pendu(int timeLimit) {
        this.attemptsLeft = maxAttempts;
    }
    
    public boolean guessLetter(char letter, String word) {
        if (word.contains(Character.toString(letter))) {
            return true;
        } else {
            attemptsLeft--;
            return false;
        }
    }
    
    public int getAttemptsLeft() { 
        return attemptsLeft; 
    }

    public boolean isGameOver() { 
        return attemptsLeft <= 0; 
    }

 }
