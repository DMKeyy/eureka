package Eureka.models.QuestionRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Eureka.models.DatabaseService;

public class WrongAnswerRepository {
    
    public static void recordWrongAnswer(int playerId, int questionId) {
        String query = "INSERT IGNORE INTO wrong_answers (question_id, player_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, questionId);
            stmt.setInt(2, playerId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Question> getPlayerWrongAnswers(int playerId) {
        List<Question> wrongQuestions = new ArrayList<>();
        String query = "SELECT q.question_id, q.question_text, m.choice_text " +
                      "FROM questions q " +
                      "JOIN wrong_answers w ON q.question_id = w.question_id " +
                      "JOIN multiple_choices m ON q.question_id = m.question_id " +
                      "WHERE w.player_id = ? AND m.is_correct = 1";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question question = new Question();
                question.setQuestion_id(rs.getInt("question_id"));
                question.setQuestionText(rs.getString("question_text"));
                question.setAnswer(rs.getString("choice_text"));
                wrongQuestions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wrongQuestions;
    }

    public static void removeWrongAnswer(int playerId, int questionId) {
        String query = "DELETE FROM wrong_answers WHERE player_id = ? AND question_id = ?";
        
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, playerId);
            stmt.setInt(2, questionId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getWrongAnswersCount(int playerId) {
        String query = "SELECT COUNT(*) as count FROM wrong_answers WHERE player_id = ?";
        
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //utiliser mor ma joueur ichof les qsts
    public static void removeAllWrongAnswers(int playerId) {
        String query = "DELETE FROM wrong_answers WHERE player_id = ?";
        
        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}