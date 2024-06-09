package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.models.Course;
import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
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


}
