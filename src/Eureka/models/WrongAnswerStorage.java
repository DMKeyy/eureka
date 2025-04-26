package Eureka.models;

import java.util.ArrayList;
import java.util.List;

import Eureka.models.QuestionRep.Question;

public class WrongAnswerStorage {

     private static final List<Question> wrongAnswers = new ArrayList<>();

    public static void addWrongAnswer(Question question) {
        if (!wrongAnswers.contains(question)) {
            wrongAnswers.add(question);
        }
    }

    public static List<Question> getWrongAnswers() {
        return new ArrayList<>(wrongAnswers);
    }

    public static boolean Contains (Question question) {
        return wrongAnswers.contains(question);
    }

    public static void clearWrongAnswers() {
        wrongAnswers.clear();
    }
}
