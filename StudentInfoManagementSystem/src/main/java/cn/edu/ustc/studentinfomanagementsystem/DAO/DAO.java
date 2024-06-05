package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
    protected boolean updateDBField(String table, String updateField, String updateValue, String keyField, String keyValue, Connection connection) {
//        String sql = "UPDATE ? SET ? = ? WHERE ? = ?";
        String sql = "UPDATE " + table + " SET " + updateField + " = ? WHERE " + keyField + " = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, updateValue);
            ps.setString(2, keyValue);
            int affectedRows = ps.executeUpdate();
            // aR = 1, success; aR = 0, fail (no student with such ID)
            return affectedRows == 1;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    protected boolean queryDBField(String table, String keyField, String keyValue, Connection connection) {
        // See if the student exists
        // SELECT * FROM StudentAccount WHERE StudentID = ?
        String sql = "SELECT * FROM " + table + " WHERE " + keyField + " = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, keyValue);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    protected String queryDBField(String table, String queryField, String keyField, String keyValue, Connection connection) {
        // SELECT StudentPassword FROM StudentAccount WHERE StudentID = ?
        String sql = "SELECT " + queryField + " FROM " + table + " WHERE " + keyField + " = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, keyValue);
/*            return ps.executeQuery().getString(queryField);*/
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // found
                    return rs.getString(queryField);
                } else {
                    // not found
                    return null;
                }
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return null;
        }
    }

    protected Float callFloatFunction(String functionName, String parameter, String alias, Connection connection) {
        // SELECT GetWeightedAverageScore('PB21111738') as WeightedAverageScore;
        String sql = "SELECT " + functionName + "(?) as " + alias;
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat(alias);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return null;
        }
    }

    protected Integer callIntFunction(String functionName, String parameter, String alias, Connection connection) {
        // SELECT GetWeightedAverageScore('PB21111738') as WeightedAverageScore;
        String sql = "SELECT " + functionName + "(?) as " + alias;
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(alias);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return null;
        }
    }

    protected String callDecimalFunction(String functionName, String parameter, String alias, Connection connection) {
        // SELECT GetWeightedAverageScore('PB21111738') as WeightedAverageScore;
        String sql = "SELECT " + functionName + "(?) as " + alias;
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(alias).toString();
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return null;
        }
    }
}
