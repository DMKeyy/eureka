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
import Eureka.models.ThemeRep.Theme;

public class QuestionRepository {
    private static Set<Integer> usedQuestionIds = new HashSet<>();

    public static Question getQuestion(Theme theme, int difficulty) {
        Question question = null;
        String query = "SELECT q.question_id, q.question_text, m.choice_text FROM questions q " +
                      "JOIN themes t ON q.id_theme = t.id_theme " +
                      "JOIN multiple_choices m ON q.question_id = m.question_id " +
                      "WHERE t.nom = ? AND q.difficulty_level = ? AND m.is_correct = 1 ";

        if (!usedQuestionIds.isEmpty()) {
            query += "AND q.question_id NOT IN (" + getUsedIdsPlaceholder() + ") ";
        }
        query += "ORDER BY RAND() LIMIT 1";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, theme.getName());
            stmt.setInt(2, difficulty);
            setUsedIds(stmt, 3);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    question = new Question();
                    question.setQuestion_id(rs.getInt("question_id"));
                    question.setQuestionText(rs.getString("question_text"));
                    question.setAnswer(rs.getString("choice_text"));
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

    public static Question getQuestionMCQ(Theme theme, int difficulty) {
        Question question = null;
        String getRandomQuestionQuery = "SELECT q.question_id, q.question_text " +
                                      "FROM questions q " +
                                      "JOIN themes t ON q.id_theme = t.id_theme " +
                                      "WHERE t.nom = ? AND q.difficulty_level = ? ";
    
        if (!usedQuestionIds.isEmpty()) {
            getRandomQuestionQuery += "AND q.question_id NOT IN (" + getUsedIdsPlaceholder() + ") ";
        }
        getRandomQuestionQuery += "ORDER BY RAND() LIMIT 1";
    
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getRandomQuestionQuery)) {
    
            stmt.setString(1, theme.getName());
            stmt.setInt(2, difficulty);
            setUsedIds(stmt, 3);
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    question = new Question();
                    int questionId = rs.getInt("question_id");
                    question.setQuestion_id(questionId);
                    question.setQuestionText(rs.getString("question_text"));
                    usedQuestionIds.add(questionId);
    
                    List<String> choices = new ArrayList<>();
                    String correctAnswer = null;
    
                    String choicesQuery = "SELECT choice_text, is_correct FROM multiple_choices WHERE question_id = ?";
                    try (PreparedStatement choicesStmt = conn.prepareStatement(choicesQuery)) {
                        choicesStmt.setInt(1, questionId);
    
                        try (ResultSet choicesRs = choicesStmt.executeQuery()) {
                            while (choicesRs.next()) {
                                String choice = choicesRs.getString("choice_text");
                                choices.add(choice);
                                if (choicesRs.getBoolean("is_correct")) {
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