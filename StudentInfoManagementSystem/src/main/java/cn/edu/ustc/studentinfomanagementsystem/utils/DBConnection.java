package cn.edu.ustc.studentinfomanagementsystem.utils;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/lab02";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // get a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // close connection
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // close preparedStatement
    public static void close(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // close resultSet
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // close all
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        close(resultSet);
        close(preparedStatement);
        close(connection);
    }

    public static void main(String[] args) {
        String sql = "SELECT * FROM StudentGrade WHERE StudentID = ?";
        String studentID = "PB21111738";
        // test connection
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            // replace the first placeholder with studentID
            ps.setString(1, studentID);
            // execute the query
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Fields: StudentID, CourseName, CourseID, Credits, Score, Status
                    System.out.println(
                            rs.getString("StudentID") + " " +
                            rs.getString("CourseName") + " " +
                            rs.getString("CourseID") + " " +
                            rs.getString("Credits") + " " +
                            rs.getString("Score") + " " +
                            rs.getString("Status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

