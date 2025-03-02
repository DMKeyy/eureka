package Eureka.models;

import java.util.List;

public class Question {
    String questionText; // texte de la qst
    String answer; // reponse correcte
    String theme; // theme de la qst
    List<String> multipleChoices; // options pour les qcm
    String explanation; // explication detaillée après avoir donner la réponse
    
    public Question(String questionText, String answer, String theme, List<String> multipleChoices, String explanation) {
        this.questionText = questionText;
        this.answer = answer;
        this.theme = theme;
        this.multipleChoices = multipleChoices;
        this.explanation = explanation;
    }
    
    public boolean checkAnswer(String userAnswer) {
        return this.answer.equalsIgnoreCase(userAnswer);
    }
    
    public String getQuestionText() { 
        return questionText; 
    }

    public String getAnswer() { 
        return answer; 
    }

    public String getTheme() { 
        return theme; 
    }

    public List<String> getMultipleChoices() { 
        return multipleChoices; 
    }

    public String getExplanation() { 
        return explanation; 
    }
}
