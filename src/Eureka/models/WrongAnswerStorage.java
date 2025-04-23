package Eureka.models;

import java.util.ArrayList;
import java.util.List;

import Eureka.models.QuestionRep.Question;

public class WrongAnswerStorage {

     private static final List<Question> wrongAnswers = new ArrayList<>();

    public static void addWrongAnswer(Question question) {
        wrongAnswers.add(question);
    }

    public static List<Question> getWrongAnswers() {
        return new ArrayList<>(wrongAnswers);
    }

    public static void clearWrongAnswers() {
        wrongAnswers.clear();
    }
}
