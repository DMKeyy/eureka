import java.time.LocalDate;

public class User {

    protected String username;
    protected String password; 
    protected String email; 
    protected LocalDate registrationDate; // date d'inscription
   // protected String dateOfBirth; 

   
    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.registrationDate = LocalDate.now(); 
       // this.dateOfBirth = dateOfBirth;
    }
    
    public String getUsername() {
        return username;   
    }

    public String getPassword() { 
        return password; 
    }

    public String getEmail() {
        return email; 
    }

    public LocalDate getRegistrationDate() { 
      return registrationDate; 
    }


   // public String getDateOfBirth() {
   //     return dateOfBirth; 
   // }
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
