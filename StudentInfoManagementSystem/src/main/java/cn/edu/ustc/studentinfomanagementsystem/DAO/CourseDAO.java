package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.models.Course;
import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO extends DAO {
    // update a string field with one key-value pair
    private static boolean updateCourseField(String updateField, String updateValue, String keyValue, Connection connection) {
        return updateDBField("Course", updateField, updateValue, "CourseID", keyValue, connection);
    }

    public static List<Course> queryCourses() {
        String sql = "SELECT * FROM Course";
        List<Course> courses = new ArrayList<>();
        try (
            Connection conn = DBConnection.getConnection(true);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                String courseName = rs.getString("CourseName");
                String courseID = rs.getString("CourseID");
                String credits = rs.getBigDecimal("Credits").toString();

                Course course = new Course(courseName, courseID, credits);
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return courses;
        }
    }

    // the three fields of Course are not nullable
    public static boolean insertCourse(@NotNull Course course) {
        String sql = "INSERT INTO Course(CourseID, CourseName, Credits) VALUES (?, ?, ?)";
        String courseID = course.getCourseID();
        String courseName = course.getCourseName();
        // still, we have this judgement just in case
        if (course.getCredits() == null) {
            return false;
        }
        BigDecimal credits = new BigDecimal(course.getCredits());
        try (
            Connection conn = DBConnection.getConnection(true);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, courseID);
            ps.setString(2, courseName);
            ps.setBigDecimal(3, credits);
            int affectedRows = ps.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    //
    public static boolean updateCourse(String oldCourseID, String newCourseID, String newCourseName, String newCredits) {
        // check the primary key
        if (isStringEmpty(oldCourseID)) {
            return false;
        }
        // check if the course exists
        List<Course> courses = queryCourses();
        boolean courseExists = false;
        for (Course course : courses) {
            if (course.getCourseID().equals(oldCourseID)) {
                courseExists = true;
                break;
            }
        }
        if (!courseExists) {
            return false;
        }
        // update the course
        // check if there is a change
        if (isStringEmpty(newCourseID) && isStringEmpty(newCourseName) && isStringEmpty(newCredits)) {
            return false;
        }
        // create a new connection and disable auto-commit
        try (
            Connection conn = DBConnection.getConnection(true)
        ) {
            conn.setAutoCommit(false);
            // update the course name
            if (!isStringEmpty(newCourseName)) {
                if (!updateCourseField("CourseName", newCourseName, oldCourseID, conn)) {
                    conn.rollback();
                    return false;
                }
            }
            // update the credits
            if (!isStringEmpty(newCredits)) {
                if (!updateCourseField("Credits", newCredits, oldCourseID, conn)) {
                    conn.rollback();
                    return false;
                }
            }
            // update the course ID
            if (!isStringEmpty(newCourseID)) {
                // CALL UpdateCourseID('011127', '011128');
                if (!callProcedure("UpdateCourseID", oldCourseID, newCourseID, conn)) {
                    conn.rollback();
                    return false;
                }
            }
            // commit the transaction
            conn.commit();
            return true;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static boolean deleteCourse(String courseID) {
        // delete the course
        try (Connection conn = DBConnection.getConnection(true)) {
            // CALL DeleteCourse('011103');
            return callProcedure("DeleteCourse", courseID, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }
}
