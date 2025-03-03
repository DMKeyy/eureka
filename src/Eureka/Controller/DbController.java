package Eureka.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class DbController {

    public static void changeScene(ActionEvent event,String fxml){
        try {
            Parent root = FXMLLoader.load(DbController.class.getResource("/Eureka/View/fxml/"+fxml));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Platform.runLater(() -> stage.centerOnScreen());
            stage.show();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void signUpUser(ActionEvent event,String username,String password) {
        Connection connection =null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;

        ResultSet rs = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eureka","root","Anis@2025");
            psCheckUserExist = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExist.setString(1,username);
            rs = psCheckUserExist.executeQuery();
            if (rs.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Username already exists");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users(username,password,registrationDate) VALUES(?,?,?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
                psInsert.executeUpdate();
                changeScene(event, "LoggedIn.fxml");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                if (connection != null) {
                    connection.close();
                }
                if (psInsert != null) {
                    psInsert.close();
                }
                if (psCheckUserExist != null) {
                    psCheckUserExist.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loginUser(ActionEvent event,String username,String password) {
        Connection connection =null;
        PreparedStatement psCheckUserExist = null;
        ResultSet rs = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eureka","root","Anis@2025");
            psCheckUserExist = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            psCheckUserExist.setString(1,username);
            rs = psCheckUserExist.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    String dbPassword = rs.getString("password");
                    if (dbPassword.equals(password)) {
                        changeScene(event, "LoggedIn.fxml");
                    } else {
                        System.out.println("Password is incorrect");
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Username or password is incorrect");
                        alert.show();}
                }
            } else {
                System.out.println("User not found in database ");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Username or password is incorrect, if you dont have an account please sign up");
                alert.titleProperty().set("An error occured");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                if (connection != null) {
                    connection.close();
                }
                if (psCheckUserExist != null) {
                    psCheckUserExist.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }

}