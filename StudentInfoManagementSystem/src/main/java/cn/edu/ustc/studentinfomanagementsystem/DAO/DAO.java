package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    protected boolean updateDBField(String table, String updateField, Date updateDate, String keyField, String keyValue, Connection connection) {
//        String sql = "UPDATE ? SET ? = ? WHERE ? = ?";
        String sql = "UPDATE " + table + " SET " + updateField + " = ? WHERE " + keyField + " = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setDate(1, updateDate);
            ps.setString(2, keyValue);
            int affectedRows = ps.executeUpdate();
            // aR = 1, success; aR = 0, fail (no student with such ID)
            return affectedRows == 1;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    protected List<String> queryDBField(String table, String queryField) {
        // SELECT StudentID FROM StudentInfo
        String sql = "SELECT " + queryField + " FROM " + table;
        List<String> resultList = new ArrayList<>();
        try (
                PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                resultList.add(rs.getString(queryField));
            }
            return resultList;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return resultList;
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
                    Float result = rs.getFloat(alias);
                    if (rs.wasNull()) {
                        return null;
                    } else {
                        return result;
                    }
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
//                    return rs.getInt(alias);
                    Integer result = rs.getInt(alias);
                    if (rs.wasNull()) {
                        return null;
                    } else {
                        return result;
                    }
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
                    BigDecimal result = rs.getBigDecimal(alias);
                    if (rs.wasNull()) {
                        return null;
                    } else {
                        return result.toString();
                    }
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return null;
        }
    }

    protected boolean callProcedure(String procedureName, String argument, Connection connection) {
        // CALL DeleteStudent('PB22011111');
        String callableSQL = "{CALL " + procedureName + "(?)}";
        try (
                CallableStatement cs = connection.prepareCall(callableSQL)
        ) {
            cs.setString(1, argument);
            boolean hasResults = cs.execute();
            while (hasResults) {
                try (ResultSet rs = cs.getResultSet()) {
                    // process result set
                    while (rs.next()) {
                        String message = rs.getString("Message");
                        // Message exists: operation successful
                        return true;
                    }
                }
                hasResults = cs.getMoreResults();
            }
            return false;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    protected boolean callProcedure(String procedureName, String arg1, String arg2, Connection connection) {
        // CALL UpdateStudentID('PB21111738', 'PB22111738');
        String callableSQL = "{CALL " + procedureName + "(?, ?)}";
        try (
                CallableStatement cs = connection.prepareCall(callableSQL)
        ) {
            cs.setString(1, arg1);
            cs.setString(2, arg2);
            boolean hasResults = cs.execute();
            while (hasResults) {
                try (ResultSet rs = cs.getResultSet()) {
                    // process result set
                    while (rs.next()) {
                        String message = rs.getString("Message");
                        // Message exists: operation successful
                        return true;
                    }
                }
                hasResults = cs.getMoreResults();
            }
            return false;
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }
}
