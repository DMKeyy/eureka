package Eureka.models.QuestionRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Eureka.models.DatabaseService;

public class QuestionRepository {
    private static Set<Integer> usedQuestionIds = new HashSet<>();

    public static Question getQuestion(String theme, int difficulty) {
        Question question = null;
        String query = "SELECT q.ID_question, q.Question_text, m.Choice_text FROM questions q JOIN multiple_choices m ON q.ID_question = m.ID_question WHERE q.Theme = ? AND q.Difficulty_level = ? AND m.Is_correct = 1 "; 

        if (!usedQuestionIds.isEmpty()) {
            query += "AND q.ID_question NOT IN (" + getUsedIdsPlaceholder() + ") ";
        }
        query += "ORDER BY RAND() LIMIT 1";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, theme);
            stmt.setInt(2, difficulty);
            setUsedIds(stmt, 3);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    question = new Question();
                    question.setQuestion_id(rs.getInt("ID_question"));
                    question.setQuestionText(rs.getString("Question_text"));
                    question.setAnswer(rs.getString("Choice_text"));
                    usedQuestionIds.add(question.getQuestion_id());
                }
                else {
                    resetUsedQuestions();
                    return getQuestion(theme, difficulty);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    public static Question getQuestionMCQ(String theme, int difficulty) {
        Question question = null;
        String getRandomQuestionQuery = "SELECT ID_question, Question_text FROM questions WHERE Theme = ? AND Difficulty_level = ? ";
    
        if (!usedQuestionIds.isEmpty()) {
            getRandomQuestionQuery += "AND ID_question NOT IN (" + getUsedIdsPlaceholder() + ") ";
        }
        getRandomQuestionQuery += "ORDER BY RAND() LIMIT 1";
    
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getRandomQuestionQuery)) {
    
            stmt.setString(1, theme);
            stmt.setInt(2, difficulty);
            setUsedIds(stmt, 3);
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    question = new Question();
                    int questionId = rs.getInt("ID_question");
                    question.setQuestion_id(questionId);
                    question.setQuestionText(rs.getString("Question_text"));
                    usedQuestionIds.add(questionId);
    
                    List<String> choices = new ArrayList<>();
                    String correctAnswer = null;
    
                    String choicesQuery = "SELECT Choice_text, Is_correct FROM multiple_choices WHERE ID_question = ?";
                    try (PreparedStatement choicesStmt = conn.prepareStatement(choicesQuery)) {
                        choicesStmt.setInt(1, questionId);
    
                        try (ResultSet choicesRs = choicesStmt.executeQuery()) {
                            while (choicesRs.next()) {
                                String choice = choicesRs.getString("Choice_text");
                                choices.add(choice);
                                if (choicesRs.getBoolean("Is_correct")) {
                                    correctAnswer = choice;
                                }
                            }
                        }
                    }
    
                    question.setMultipleChoices(choices);
                    question.setAnswer(correctAnswer);
                } else {
                    resetUsedQuestions();
                    return getQuestionMCQ(theme, difficulty);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    public static void resetUsedQuestions() {
        usedQuestionIds.clear();
    }

    private static String getUsedIdsPlaceholder() {
        return usedQuestionIds.isEmpty() ? "NULL" : String.join(",", Collections.nCopies(usedQuestionIds.size(), "?"));
    }

    private static void setUsedIds(PreparedStatement stmt, int startIndex) throws SQLException {
        int index = startIndex;
        for (int id : usedQuestionIds) {
            stmt.setInt(index++, id);
        }
    }
}