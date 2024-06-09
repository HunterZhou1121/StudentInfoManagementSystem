package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.models.Student;
import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.Comparator;
import java.util.List;

public class StudentDAO extends DAO {
    private static boolean updateStudentField(String updateField, String updateValue, String keyValue, Connection connection) {
        return updateDBField("Student", updateField, updateValue, "ID", keyValue, connection);
    }

    private static boolean updateEnrolmentField(String updateField, String updateValue, String keyValue, Connection connection) {
        return updateDBField("Enrolment", updateField, updateValue, "StudentID", keyValue, connection);
    }

    private static boolean updateEnrolmentField(String updateField, Date updateDate, String keyValue, Connection connection) {
        return updateDBField("Enrolment", updateField, updateDate, "StudentID", keyValue, connection);
    }

    public static boolean updateStudentPhoneNumber(String phoneNumber, String ID) {
        // for this student, update the phone number in the database
        if (ID == null || phoneNumber == null) {
            return false;
        }
        try (Connection conn = DBConnection.getConnection(true)) {
            return updateStudentField("PhoneNumber", phoneNumber, ID, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static boolean updateStudentEmail(String email, String ID) {
        // for this student, update the email in the database
        if (ID == null || email == null) {
            return false;
        }
        try (Connection conn = DBConnection.getConnection(true)) {
            return updateStudentField("Email", email, ID, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static boolean updateStudentPhotoURL(String photoURL, String ID) {
        // for this student, update the email in the database
        if (ID == null || photoURL == null) {
            return false;
        }
        try (Connection conn = DBConnection.getConnection(true)) {
            return updateStudentField("PhotoURL", photoURL, ID, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static boolean updateStudentName(String name, String ID) {
        // for this student, update the name in the database
        if (ID == null || name == null) {
            return false;
        }
        try (Connection conn = DBConnection.getConnection(true)) {
            return updateStudentField("Name", name, ID, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static boolean updateStudentGender(String gender, String ID) {
        // for this student, update the gender in the database
        if (ID == null || gender == null) {
            return false;
        }
        try (Connection conn = DBConnection.getConnection(true)) {
            return updateStudentField("Gender", gender, ID, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static boolean updateStudentEthnicity(String ethnicity, String ID) {
        // for this student, update the ethnicity in the database
        if (ID == null || ethnicity == null) {
            return false;
        }
        try (Connection connection = DBConnection.getConnection(true)) {
            return updateStudentField("Ethnicity", ethnicity, ID, connection);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static boolean updateStudentPoliticalAffiliation(String politicalAffiliation, String ID) {
        // for this student, update the political affiliation in the database
        if (ID == null || politicalAffiliation == null) {
            return false;
        }
        try (Connection conn = DBConnection.getConnection(true)) {
            return updateStudentField("PoliticalAffiliation", politicalAffiliation, ID, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static boolean updateStudentID(String newStudentID, String oldStudentID) {
        // for this student, update the student ID in the database
        if (oldStudentID == null || newStudentID == null) {
            return false;
        }
        // CALL UpdateStudentID('PB21111738', 'PB22111738');
        try (Connection conn = DBConnection.getConnection(true)) {
            return callProcedure("UpdateStudentID", oldStudentID, newStudentID, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static boolean updateStudentMajor(String newMajor, String studentID) {
        // for this student, update the major in the database
        if (studentID == null || newMajor == null) {
            return false;
        }
        try (Connection connection = DBConnection.getConnection(true)) {
            return updateEnrolmentField("Major", newMajor, studentID, connection);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static @Nullable Student queryStudent(@NotNull String studentID) {
        // query the database to get the rest of the information
        String sql  = "SELECT * FROM StudentInfo WHERE StudentID = ?";
        try (
            Connection conn = DBConnection.getConnection(true);
            PreparedStatement ps = conn.prepareStatement(sql)
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

    public static @Nullable List<String> queryStudentIDs() {
        // SELECT StudentID FROM StudentInfo
        try (Connection conn = DBConnection.getConnection(true)) {
            return queryDBField("StudentInfo", "StudentID", conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return null;
        }
    }

    public static List<String> queryStudentIDsASEC() {
        List<String> studentIDs = queryStudentIDs();
        if (studentIDs != null) {
            studentIDs.sort(String::compareTo);
        }
        return studentIDs;
    }

    public static List<String> queryStudentIDsDESC() {
        List<String> studentIDs = queryStudentIDs();
        if (studentIDs != null) {
            studentIDs.sort(Comparator.reverseOrder());
        }
        return studentIDs;
    }

    public static boolean insertStudent(@NotNull Student student) {
        // insert into Student first
        // INSERT INTO Student(ID, Name, Gender, DOB, Ethnicity, PoliticalAffiliation)
        // VALUES ('123456200211212333', 'Ezra Bridger', '男', '2002-11-21', '汉', '共青团员');
        String sql = "INSERT INTO Student(ID, Name, Gender, DOB, Ethnicity, PoliticalAffiliation) VALUES (?, ?, ?, ?, ?, ?)";
        boolean success = false;
        try (Connection conn = DBConnection.getConnection(true)) {
            // both insertions should be successful at the same time
            conn.setAutoCommit(false);
            try (
                PreparedStatement ps = conn.prepareStatement(sql)
            ) {
                ps.setString(1, student.getID());
                ps.setString(2, student.getName());
                ps.setString(3, student.getGender());
                ps.setDate(4, student.getDOB());
                ps.setString(5, student.getEthnicity());
                ps.setString(6, student.getPoliticalAffiliation());
                success = ps.executeUpdate() == 1;
            }
            if (!success) {
                return false;
            }
            // insert into Enrolment
            // INSERT INTO Enrolment(StudentID, ID, EnrolmentDate, Major) VALUES ('PB21111738', '123456200211212333', '2021-09-01', '计算机科学与技术');
            sql = "INSERT INTO Enrolment(StudentID, ID, EnrolmentDate, Major) VALUES (?, ?, ?, ?)";
            try (
                PreparedStatement ps = conn.prepareStatement(sql)
            ) {
                ps.setString(1, student.getStudentID());
                ps.setString(2, student.getID());
                ps.setDate(3, student.getEnrolmentDate());
                ps.setString(4, student.getMajor());
                success = ps.executeUpdate() == 1;
            }
            if (success) {
                conn.commit();
            } else {
                conn.rollback();
            }
            return success;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static boolean deleteStudent(String studentID) {
        // CALL DeleteStudent('PB22011111');
        try (Connection conn = DBConnection.getConnection(true)) {
            return callProcedure("DeleteStudent", studentID, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static void main(String[] args) {
        List<String> studentIDs = queryStudentIDsASEC();
        for (String studentID : studentIDs) {
            System.out.println(studentID);
        }
    }
}
