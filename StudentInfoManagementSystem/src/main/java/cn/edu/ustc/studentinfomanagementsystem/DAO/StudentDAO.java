package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.models.Student;
import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.Comparator;
import java.util.List;

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

    private boolean updateDBField(String table, String updateField, Date updateDate, String keyField, String keyValue) {
        return super.updateDBField(table, updateField, updateDate, keyField, keyValue, connection);
    }

    private boolean updateStudentField(String updateField, String updateValue, String keyValue) {
        return updateDBField("Student", updateField, updateValue, "ID", keyValue);
    }

    private boolean updateEnrolmentField(String updateField, String updateValue, String keyValue) {
        return updateDBField("Enrolment", updateField, updateValue, "StudentID", keyValue);
    }

    private boolean updateEnrolmentField(String updateField, Date updateDate, String keyValue) {
        return updateDBField("Enrolment", updateField, updateDate, "StudentID", keyValue);
    }

    private boolean callProcedure(String procedureName, String argument) {
        return super.callProcedure(procedureName, argument, connection);
    }

    private boolean callProcedure(String procedureName, String arg1, String arg2) {
        return super.callProcedure(procedureName, arg1, arg2, connection);
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

    public boolean updateStudentName(String name, String ID) {
        // for this student, update the name in the database
        if (ID == null || name == null) {
            return false;
        }
        return updateStudentField("Name", name, ID);
    }

    public boolean updateStudentGender(String gender, String ID) {
        // for this student, update the gender in the database
        if (ID == null || gender == null) {
            return false;
        }
        return updateStudentField("Gender", gender, ID);
    }

    public boolean updateStudentEthnicity(String ethnicity, String ID) {
        // for this student, update the ethnicity in the database
        if (ID == null || ethnicity == null) {
            return false;
        }
        return updateStudentField("Ethnicity", ethnicity, ID);
    }

    public boolean updateStudentPoliticalAffiliation(String politicalAffiliation, String ID) {
        // for this student, update the political affiliation in the database
        if (ID == null || politicalAffiliation == null) {
            return false;
        }
        return updateStudentField("PoliticalAffiliation", politicalAffiliation, ID);
    }

    public boolean updateStudentID(String newStudentID, String oldStudentID) {
        // for this student, update the student ID in the database
        if (oldStudentID == null || newStudentID == null) {
            return false;
        }
        // CALL UpdateStudentID('PB21111738', 'PB22111738');
        return callProcedure("UpdateStudentID", oldStudentID, newStudentID);
    }

//    public boolean updateStudentEnrolmentDate(Date newEnrolmentDate, String studentID) {
//        // for this student, update the enrolment date in the database
//        if (studentID == null || newEnrolmentDate == null) {
//            return false;
//        }
//        return updateEnrolmentField("EnrolmentDate", newEnrolmentDate, studentID);
//    }

    public boolean updateStudentMajor(String newMajor, String studentID) {
        // for this student, update the major in the database
        if (studentID == null || newMajor == null) {
            return false;
        }
        return updateEnrolmentField("Major", newMajor, studentID);
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

    public List<String> queryStudentIDs() {
        // SELECT StudentID FROM StudentInfo
        return super.queryDBField("StudentInfo", "StudentID");
    }

    public List<String> queryStudentIDsASEC() {
        List<String> studentIDs = queryStudentIDs();
        studentIDs.sort(String::compareTo);
        return studentIDs;
    }

    public List<String> queryStudentIDsDESC() {
        List<String> studentIDs = queryStudentIDs();
        studentIDs.sort(Comparator.reverseOrder());
        return studentIDs;
    }

    public boolean insertStudent(@NotNull Student student) {
        // insert into Student first
        // INSERT INTO Student(ID, Name, Gender, DOB, Ethnicity, PoliticalAffiliation)
        // VALUES ('123456200211212333', 'Ezra Bridger', '男', '2002-11-21', '汉', '共青团员');
        String sql = "INSERT INTO Student(ID, Name, Gender, DOB, Ethnicity, PoliticalAffiliation) VALUES (?, ?, ?, ?, ?, ?)";
        boolean success = false;
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, student.getID());
            ps.setString(2, student.getName());
            ps.setString(3, student.getGender());
            ps.setDate(4, student.getDOB());
            ps.setString(5, student.getEthnicity());
            ps.setString(6, student.getPoliticalAffiliation());
            success = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
        if (!success) {
            return false;
        }
        // insert into Enrolment
        // INSERT INTO Enrolment(StudentID, ID, EnrolmentDate, Major) VALUES ('PB21111738', '123456200211212333', '2021-09-01', '计算机科学与技术');
        sql = "INSERT INTO Enrolment(StudentID, ID, EnrolmentDate, Major) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, student.getStudentID());
            ps.setString(2, student.getID());
            ps.setDate(3, student.getEnrolmentDate());
            ps.setString(4, student.getMajor());
            success = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
        return success;
    }

    public boolean deleteStudent(String studentID) {
        // CALL DeleteStudent('PB22011111');
        return callProcedure("DeleteStudent", studentID);
    }

    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();
        List<String> studentIDs = studentDAO.queryStudentIDsASEC();
        for (String studentID : studentIDs) {
            System.out.println(studentID);
        }
    }
}
