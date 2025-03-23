package Eureka.models;

import java.util.List;

public class Question {
    private int question_id;
    private String questionText;
    private String answer;
    private String theme;
    private List<String> multipleChoices;
    private String explanation;
    private int difficultyLevel; 
    
    public Question(int question_id,String questionText, String answer, String theme, List<String> multipleChoices, String explanation, int difficultyLevel) {
        this.question_id = question_id;
        this.questionText = questionText;
        this.answer = answer;
        this.theme = theme;
        this.multipleChoices = multipleChoices;
        this.explanation = explanation;
        this.question_id = question_id;
        this.difficultyLevel = difficultyLevel;
    }
    
    public Question() {
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

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setMultipleChoices(List<String> multipleChoices) {
        this.multipleChoices = multipleChoices;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

}
