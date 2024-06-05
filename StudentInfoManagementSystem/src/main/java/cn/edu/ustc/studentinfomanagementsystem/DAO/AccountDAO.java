package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountDAO extends DAO {
    private Connection connection;

    public AccountDAO() {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
        }
    }

    public String queryAccountPassword(String table, String UsernameFieldName, String PasswordFieldName, String username) {
        return super.queryDBField(table, PasswordFieldName, UsernameFieldName, username, connection);
    }

    public String queryStudentPassword(String studentUsername) {
        return queryAccountPassword("StudentAccount", "StudentUsername", "StudentPassword", studentUsername);
    }

    public String queryTeacherPassword(String teacherUsername) {
        return queryAccountPassword("TeacherAccount", "TeacherUsername", "TeacherPassword", teacherUsername);
    }
    
    // authenticate the student
    public String authenticateStudent(String studentUsername, String studentPassword) {
        // username exists?
        String correctPassword = queryStudentPassword(studentUsername);
        if (correctPassword == null) {
            return "Does not exist.";
        }
        // password correct?
        if (studentPassword.equals(correctPassword)) {
            return "Success.";
        } else {
            return "Incorrect password.";
        }
    }

    // authenticate the teacher
    public String authenticateTeacher(String teacherUsername, String teacherPassword) {
        // username exists?
        String correctPassword = queryTeacherPassword(teacherUsername);
        if (correctPassword == null) {
            return "Does not exist.";
        }
        // password correct?
        if (teacherPassword.equals(correctPassword)) {
            return "Success.";
        } else {
            return "Incorrect password.";
        }
    }
}
