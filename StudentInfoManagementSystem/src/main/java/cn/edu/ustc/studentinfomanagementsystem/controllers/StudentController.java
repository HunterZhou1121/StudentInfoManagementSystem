package cn.edu.ustc.studentinfomanagementsystem.controllers;

import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import cn.edu.ustc.studentinfomanagementsystem.SceneManager;
import cn.edu.ustc.studentinfomanagementsystem.utils.alertUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentController {
    private String StudentID;

    private String StudentName;

    @FXML private Label welcomeLabel;

    @FXML private Button logoutButton;

    @FXML
    private void handleLogoutButtonAction() {
        // reset
        setStudentID(null);
        setStudentName(null);
        // switch to the login view
        SceneManager.getInstance().switchScene("login-view");
        // delete the student view
        SceneManager.getInstance().clearScene("student-view");
        // alert: "注销成功"
        alertUtils.showAlert("登出成功", "登出成功！");
    }

    public void QueryStudentInfo() {
        // Query student info from the database
        String sql  = "SELECT * FROM StudentInfo WHERE StudentID = ?";
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, StudentID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // ID, PhotoURL, Name, Gender, DOB, Ethnicity, PoliticalAffiliation,
                    // PhoneNumber, Email, StudentID, EnrolmentDate, Major
                    setStudentName(rs.getString("Name"));
                }
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
        }
        welcomeLabel.setText("欢迎您，" + StudentName + "！");
    }
    public void setStudentID(String StudentID) {
        this.StudentID = StudentID;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
    }

}
