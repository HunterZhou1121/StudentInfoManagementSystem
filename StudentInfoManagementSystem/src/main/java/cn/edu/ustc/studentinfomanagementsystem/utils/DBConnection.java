package cn.edu.ustc.studentinfomanagementsystem.utils;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/lab02";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static DBConnection instance;

    private final Connection connection;

    private DBConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    // get a connection to the database
    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    // close connection
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                SQLExceptionHandler(e);
            }
        }
    }

    // close preparedStatement
    public static void close(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                SQLExceptionHandler(e);
            }
        }
    }

    // close resultSet
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                SQLExceptionHandler(e);
            }
        }
    }

    // close all
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        close(resultSet);
        close(preparedStatement);
        close(connection);
    }

    // exception handling
    public static void SQLExceptionHandler(@NotNull SQLException e) {
        String sqlState = e.getSQLState();
        String errorCode = String.valueOf(e.getErrorCode());
        String message = e.getMessage();
        System.out.println("SQLState: " + sqlState);
        System.out.println("ErrorCode: " + errorCode);
        System.out.println("Message: " + message);
    }

    public static void main(String[] args) {
        String sql = "SELECT * FROM StudentGrade WHERE StudentID = ?";
        String studentID = "PB21111738";
        // test connection
        try (
            PreparedStatement ps = getInstance().getConnection().prepareStatement(sql)
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
            SQLExceptionHandler(e);
        }
    }
}

