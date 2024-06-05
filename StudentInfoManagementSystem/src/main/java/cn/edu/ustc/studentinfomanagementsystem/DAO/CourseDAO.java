package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.models.Course;
import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO extends DAO {
    private Connection connection;

    public CourseDAO() {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
        }
    }

    public List<Course> querySelectedCourses(String studentID) {
        String sql = "SELECT * FROM StudentGrade WHERE StudentID = ?";
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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

    public List<Course> queryAllCourses(String studentID) {
        String sql = "SELECT * FROM StudentGrade WHERE StudentID = ?";
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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

    public List<Course> queryGrades(String studentID) {
        String sql = "SELECT * FROM StudentGrade WHERE StudentID = ?";
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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

    public String getPassedCredits(String studentID) {
        // SELECT GetPassedCredits('PB21111738') as PassedCredits;
        return callDecimalFunction("GetPassedCredits", studentID, "PassedCredits", connection);
    }

    public Integer getFailedCourseNum(String studentID) {
        // SELECT GetFailedCourseNum('PB21111738') as FailedCourseNum;
        return callIntFunction("GetFailedCourseNum", studentID, "FailedCourseNum", connection);
    }

    public String getFailedCredits(String studentID) {
        // SELECT GetFailedCredits('PB21111738') as FailedCredits;
        return callDecimalFunction("GetFailedCredits", studentID, "FailedCredits", connection);
    }

    public Float getWeightedAverageScore(String studentID) {
        // SELECT GetWeightedAverageScore('PB21111738') as WeightedAverageScore;
        return callFloatFunction("GetWeightedAverageScore", studentID, "WeightedAverageScore", connection);
    }

    public static void main(String[] args) {
        CourseDAO courseDAO = new CourseDAO();
        String studentID = "PB21111738";
        List<Course> allCourses = courseDAO.queryAllCourses(studentID);
        System.out.println("该学生所有课程：");
        for (Course course : allCourses) {
            System.out.println(course.toString());
        }
        List<Course> selectedCourses = courseDAO.querySelectedCourses(studentID);
        System.out.println("该学生当前已选课程：");
        for (Course course : selectedCourses) {
            System.out.println(course.toString());
        }
        List<Course> grades = courseDAO.queryGrades(studentID);
        System.out.println("该学生成绩：");
        for (Course grade : grades) {
            System.out.println(grade.toString());
        }
        studentID = "PB23021112";
        System.out.println("该学生已通过学分：" + courseDAO.getPassedCredits(studentID));
        System.out.println("该学生已挂科门数：" + courseDAO.getFailedCourseNum(studentID));
        System.out.println("该学生已挂科学分：" + courseDAO.getFailedCredits(studentID));
        System.out.println("该学生加权平均分：" + courseDAO.getWeightedAverageScore(studentID));
    }
}
