package cn.edu.ustc.studentinfomanagementsystem.controllers;

import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;
import cn.edu.ustc.studentinfomanagementsystem.DAO.AccountDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import cn.edu.ustc.studentinfomanagementsystem.SceneManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginController extends Controller {
    private final AccountDAO accountDAO = new AccountDAO();

    @FXML private AnchorPane anchorPane;

    @FXML private Label label;

    @FXML private Button loginButton;

    @FXML private Button returnButton;

    @FXML private TextField usernameField;

    @FXML private PasswordField passwordField;

    @FXML private ComboBox<String> userTypeComboBox;


    @FXML
    private void initialize() {
        userTypeComboBox.setItems(FXCollections.observableArrayList("教师", "学生"));
        userTypeComboBox.getSelectionModel().select(0);
    }

    @FXML
    private void handleLoginButtonAction() {
        String userType = userTypeComboBox.getValue();
        String username = usernameField.getText();
        String password = passwordField.getText();
        switch (userType) {
            case "教师" -> {
                // Query account info from the database
//            String sql = "SELECT * FROM TeacherAccount WHERE TeacherUsername = ?";
//            try (
//                Connection conn = DBConnection.getInstance().getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql)
//            ) {
//                // Set the parameter
//                ps.setString(1, username);
//                // Execute the query
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        // username found
//                        String correctPassword = rs.getString("TeacherPassword");
//                        if (password.equals(correctPassword)) {
//                            // Switch to teacher view
//                            try {
//                                SceneManager.getInstance().addScene("teacher-view", "views/teacher-view.fxml");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            SceneManager.getInstance().switchScene("teacher-view");
//                            // empty the password field
//                            passwordField.setText("");
//                        } else {
//                            // password incorrect
//                            showAlert("登录失败", "密码错误！");
//                            // empty the password field
//                            passwordField.setText("");
//                        }
//                    } else {
//                        // username not found
//                        showAlert("登录失败", "用户名不存在！");
//                        // empty the two fields
//                        usernameField.setText("");
//                        passwordField.setText("");
//                    }
//                } catch (SQLException e) {
//                    DBConnection.SQLExceptionHandler(e);
//                }
//            } catch (SQLException e) {
//                DBConnection.SQLExceptionHandler(e);
//            }
                String authenticateResult = accountDAO.authenticateTeacher(username, password);
                switch (authenticateResult) {
                    case "Success." -> {
                        // Switch to teacher view
                        try {
                            SceneManager.getInstance().addScene("teacher-view", "views/teacher-view.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SceneManager.getInstance().switchScene("teacher-view");
                        // empty the password field
                        passwordField.setText("");
                    }
                    case "Does not exist." -> {
                        // username not found
                        showAlert("登录失败", "用户名不存在！");
                        // empty the two fields
                        usernameField.setText("");
                        passwordField.setText("");
                    }
                    case "Incorrect password." -> {
                        // password incorrect
                        showAlert("登录失败", "密码错误！");
                        // empty the password field
                        passwordField.setText("");
                    }
                }
            }
            case "学生" -> {
                // Query account info from the database
//            String sql = "SELECT * FROM StudentAccount WHERE StudentUsername = ?";
//            try (
//                Connection conn = DBConnection.getInstance().getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql)
//            ) {
//                // Set the parameter
//                ps.setString(1, username);
//                // Execute the query
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        // username found
//                        String correctPassword = rs.getString("StudentPassword");
//                        if (password.equals(correctPassword)) {
//                            // Switch to student view
//                            try {
//                                SceneManager.getInstance().addScene("student-view", "views/student-view.fxml");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            // Set the StudentID
//                            StudentController studentController = SceneManager.getInstance().getLoader("student-view").getController();
////                            studentController.setStudentID(username);
////                            studentController.QueryStudentInfo();
//                            studentController.setStudent(username);
//                            studentController.updateFields();
//                            SceneManager.getInstance().switchScene("student-view");
//                            // empty the password field
//                            passwordField.setText("");
//                        } else {
//                            // password incorrect
//                            showAlert("登录失败", "密码错误！");
//                            // empty the password field
//                            passwordField.setText("");
//                        }
//                    } else {
//                        // username not found
//                        showAlert("登录失败", "用户名不存在！");
//                        // empty the two fields
//                        usernameField.setText("");
//                        passwordField.setText("");
//                    }
//                } catch (SQLException e) {
//                    DBConnection.SQLExceptionHandler(e);
//                }
//            } catch (SQLException e) {
//                DBConnection.SQLExceptionHandler(e);
//            }
                String authenticateResult = accountDAO.authenticateStudent(username, password);
                switch (authenticateResult) {
                    case "Success." -> {
                        // Switch to student view
                        try {
                            SceneManager.getInstance().addScene("student-view", "views/student-view.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // Set the StudentID
                        StudentController studentController = SceneManager.getInstance().getLoader("student-view").getController();
                        studentController.setStudent(username);
                        studentController.updateFields();
                        SceneManager.getInstance().switchScene("student-view");
                        // empty the password field
                        passwordField.setText("");
                    }
                    case "Does not exist." -> {
                        // username not found
                        showAlert("登录失败", "用户名不存在！");
                        // empty the two fields
                        usernameField.setText("");
                        passwordField.setText("");
                    }
                    case "Incorrect password." -> {
                        // password incorrect
                        showAlert("登录失败", "密码错误！");
                        // empty the password field
                        passwordField.setText("");
                    }
                }
            }
            default -> {
                // userType not recognized
                showAlert("登录失败", "用户类型错误！");
                // empty the three fields
                usernameField.setText("");
                passwordField.setText("");
                userTypeComboBox.getSelectionModel().select(0);
            }
        }
    }

    @FXML
    private void handleReturnButtonAction() {
        SceneManager.getInstance().switchScene("hello-view");
    }
}
