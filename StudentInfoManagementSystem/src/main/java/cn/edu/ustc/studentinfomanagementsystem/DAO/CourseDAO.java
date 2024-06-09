package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.models.Course;
import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO extends DAO {
    public static List<Course> querySelectedCourses(String studentID) {
        String sql = "SELECT * FROM StudentGrade WHERE StudentID = ?";
        List<Course> courses = new ArrayList<>();
        try (
            Connection conn = DBConnection.getConnection(true);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, studentID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // selected courses: score is null
                    Integer score = rs.getInt("Score");
                    if (!rs.wasNull()) {
                        continue;
                    }
                    score = null;
                    String courseName = rs.getString("CourseName");
                    String courseID = rs.getString("CourseID");
                    String credits = rs.getBigDecimal("Credits").toString();
                    String status = rs.getString("Status");

                    Course course = new Course(studentID, courseName, courseID, credits, score, status);
                    courses.add(course);
                }
                return courses;
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return courses;
        }
    }

    public static List<Course> queryAllCourses(String studentID) {
        String sql = "SELECT * FROM StudentGrade WHERE StudentID = ?";
        List<Course> courses = new ArrayList<>();
        try (
            Connection conn = DBConnection.getConnection(true);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, studentID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer score = rs.getInt("Score");
                    if (rs.wasNull()) {
                        score = null;
                    }
                    String courseName = rs.getString("CourseName");
                    String courseID = rs.getString("CourseID");
                    String credits = rs.getBigDecimal("Credits").toString();
                    String status = rs.getString("Status");

                    Course course = new Course(studentID, courseName, courseID, credits, score, status);
                    courses.add(course);
                }
                return courses;
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return courses;
        }
    }

    public static List<Course> queryGrades(String studentID) {
        String sql = "SELECT * FROM StudentGrade WHERE StudentID = ?";
        List<Course> courses = new ArrayList<>();
        try (
            Connection conn = DBConnection.getConnection(true);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, studentID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // grades: score is not null
                    Integer score = rs.getInt("Score");
                    if (rs.wasNull()) {
                        continue;
                    }
                    String courseName = rs.getString("CourseName");
                    String courseID = rs.getString("CourseID");
                    String credits = rs.getBigDecimal("Credits").toString();
                    String status = rs.getString("Status");

                    Course course = new Course(studentID, courseName, courseID, credits, score, status);
                    courses.add(course);
                }
                return courses;
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return courses;
        }
    }

    public static @Nullable String getPassedCredits(String studentID) {
        // SELECT GetPassedCredits('PB21111738') as PassedCredits;
        try (Connection conn = DBConnection.getConnection(true)) {
            return callDecimalFunction("GetPassedCredits", studentID, "PassedCredits", conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return null;
        }
    }

    public static @Nullable Integer getFailedCourseNum(String studentID) {
        // SELECT GetFailedCourseNum('PB21111738') as FailedCourseNum;
        try (Connection conn = DBConnection.getConnection(true)) {
            return callIntFunction("GetFailedCourseNum", studentID, "FailedCourseNum", conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return null;
        }
    }

    public static @Nullable String getFailedCredits(String studentID) {
        // SELECT GetFailedCredits('PB21111738') as FailedCredits;
        try (Connection conn = DBConnection.getConnection(true)) {
            return callDecimalFunction("GetFailedCredits", studentID, "FailedCredits", conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return null;
        }
    }

    public static @Nullable Float getWeightedAverageScore(String studentID) {
        // SELECT GetWeightedAverageScore('PB21111738') as WeightedAverageScore;
        try (Connection conn = DBConnection.getConnection(true)) {
            return callFloatFunction("GetWeightedAverageScore", studentID, "WeightedAverageScore", conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return null;
        }
    }

    public static void main(String[] args) {
        String studentID = "PB21111738";
        List<Course> allCourses = queryAllCourses(studentID);
        System.out.println("该学生所有课程：");
        for (Course course : allCourses) {
            System.out.println(course.toString());
        }
        List<Course> selectedCourses = querySelectedCourses(studentID);
        System.out.println("该学生当前已选课程：");
        for (Course course : selectedCourses) {
            System.out.println(course.toString());
        }
        List<Course> grades = queryGrades(studentID);
        System.out.println("该学生成绩：");
        for (Course grade : grades) {
            System.out.println(grade.toString());
        }
        studentID = "PB23021112";
        System.out.println("该学生已通过学分：" + getPassedCredits(studentID));
        System.out.println("该学生已挂科门数：" + getFailedCourseNum(studentID));
        System.out.println("该学生已挂科学分：" + getFailedCredits(studentID));
        System.out.println("该学生加权平均分：" + getWeightedAverageScore(studentID));
    }
}
