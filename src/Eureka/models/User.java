package Eureka.models;

import java.time.LocalDate;

public class User {

    protected String username;
    protected String password;
    protected LocalDate registrationDate;


   
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.registrationDate = LocalDate.now(); 
       // this.dateOfBirth = dateOfBirth;
    }
    
    public String getUsername() {
        return username;   
    }

    public String getPassword() { 
        return password; 
    }



    public LocalDate getRegistrationDate() { 
      return registrationDate; 
    }
}































   //protected String username; 
    //protected int score;
    //protected int dailyChallengesCompleted; 
    
  //  public User(String username) {
    //    this.username = username;
    //    this.score = 0;
      //  this.dailyChallengesCompleted = 0;
    //}
    
 //   public String getUsername() { 
   //     return username; 
    //}

//    public int getScore() {
  //      return score; 
   // }

  //  public void addScore(int points) {
    //    this.score += points; 
   // }

    //public int getDailyChallengesCompleted() {
      //  return dailyChallengesCompleted; 
    //}

   // public void TotalDailyChallenge() {
     //   this.dailyChallengesCompleted++; 
   // }
//}
