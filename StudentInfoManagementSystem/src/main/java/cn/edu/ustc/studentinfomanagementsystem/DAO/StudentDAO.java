package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.models.Student;
import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class StudentDAO extends DAO {
    private Connection connection;

    public StudentDAO() {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
        }
    }

    private boolean updateDBField(String table, String updateField, String updateValue, String keyField, String keyValue) {
        return super.updateDBField(table, updateField, updateValue, keyField, keyValue, connection);
    }

    private boolean updateStudentField(String updateField, String updateValue, String keyValue) {
        return updateDBField("Student", updateField, updateValue, "ID", keyValue);
    }

    private boolean updateEnrolmentField(String updateField, String updateValue, String keyValue) {
        return updateDBField("Enrolment", updateField, updateValue, "StudentID", keyValue);
    }

    public boolean updateStudentPhoneNumber(String phoneNumber, String ID) {
        // for this student, update the phone number in the database
        if (ID == null || phoneNumber == null) {
            return false;
        }
        return updateStudentField("PhoneNumber", phoneNumber, ID);
    }

    public boolean updateStudentEmail(String email, String ID) {
        // for this student, update the email in the database
        if (ID == null || email == null) {
            return false;
        }
        return updateStudentField("Email", email, ID);
    }

    public boolean updateStudentPhotoURL(String photoURL, String ID) {
        // for this student, update the email in the database
        if (ID == null || photoURL == null) {
            return false;
        }
//        return updateDBField("Student", "PhotoURL", photoURL, "ID", ID);
        return updateStudentField("PhotoURL", photoURL, ID);
    }
    public @Nullable Student queryStudent(@NotNull String studentID) {
        // query the database to get the rest of the information
        String sql  = "SELECT * FROM StudentInfo WHERE StudentID = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, studentID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // successfully queried
                    // required fields
                    String ID = rs.getString("ID");
                    String gender = rs.getString("Gender");
                    String name = rs.getString("Name");
                    Date DOB =  rs.getDate("DOB");
                    String ethnicity = rs.getString("Ethnicity");
                    String politicalAffiliation = rs.getString("PoliticalAffiliation");
                    Date enrolmentDate = rs.getDate("EnrolmentDate");
                    String major = rs.getString("Major");
                    // optional fields
                    String photoURL = rs.getString("PhotoURL");
                    String phoneNumber = rs.getString("PhoneNumber");
                    String email = rs.getString("Email");
                    Student s = new Student(
                            ID, name, gender, DOB, ethnicity, politicalAffiliation, studentID, enrolmentDate, major
                    );
                    s.setPhotoURL(photoURL);
                    s.setPhoneNumber(phoneNumber);
                    s.setEmail(email);
                    return s;
                } else {
                    // fail to query the student
                    return null;
                }
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            // fail to query the student
            return null;
        }
    }
}
