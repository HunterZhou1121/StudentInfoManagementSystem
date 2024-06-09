package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.models.Punishment;
import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;
import cn.edu.ustc.studentinfomanagementsystem.models.Award;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AwardPunishmentDAO extends DAO{
    // update a string field with two key-value pairs
    private static boolean updateAwardField(String updateField, String updateValue, String keyField1, String keyValue1, String keyField2, String keyValue2, Connection connection) {
        return updateDBField("Award", updateField, updateValue, keyField1, keyValue1, keyField2, keyValue2, connection);
    }

    // update a date field with two key-value pairs
    private static boolean updateAwardField(String updateField, Date updateDate, String keyField1, String keyValue1, String keyField2, String keyValue2, Connection connection) {
        return updateDBField("Award", updateField, updateDate, keyField1, keyValue1, keyField2, keyValue2, connection);
    }

    // update a string field with two key-value pairs
    private static boolean updatePunishmentField(String updateField, String updateValue, String keyField1, String keyValue1, String keyField2, String keyValue2, Connection connection) {
        return updateDBField("Punishment", updateField, updateValue, keyField1, keyValue1, keyField2, keyValue2, connection);
    }
    // update a date field with two key-value pairs
    private static boolean updatePunishmentField(String updateField, Date updateDate, String keyField1, String keyValue1, String keyField2, String keyValue2, Connection connection) {
        return updateDBField("Punishment", updateField, updateDate, keyField1, keyValue1, keyField2, keyValue2, connection);
    }

    private static boolean deleteFromAward(String studentID, String awardName) {
        try (Connection conn = DBConnection.getConnection(true)) {
            return deleteFromDBField("Award", "StudentID", studentID, "AwardName", awardName, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    private static boolean deleteFromPunishment(String studentID, String punishmentName) {
        try (Connection conn = DBConnection.getConnection(true)) {
            return deleteFromDBField("Punishment", "StudentID", studentID, "PunishmentName", punishmentName, conn);
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return false;
        }
    }

    public static List<Award> queryAwards(String studentID) {
        String sql = "SELECT * FROM Award WHERE StudentID = ?";
        List<Award> awards = new ArrayList<>();
        try (
            Connection conn = DBConnection.getConnection(true);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, studentID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String awardName = rs.getString("AwardName");
                    String awardLevel = rs.getString("AwardLevel");
                    Date awardDate = rs.getDate("AwardDate");
                    Award award = new Award(studentID, awardName, awardLevel, awardDate);
                    awards.add(award);
                }
                return awards;
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return awards;
        }
    }

    public static List<Punishment> queryPunishments(String studentID) {
        String sql = "SELECT * FROM Punishment WHERE StudentID = ?";
        List<Punishment> punishments = new ArrayList<>();
        try (
            Connection conn = DBConnection.getConnection(true);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, studentID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String punishmentName = rs.getString("PunishmentName");
                    Date punishmentDate = rs.getDate("PunishmentDate");
                    Punishment punishment = new Punishment(studentID, punishmentName, punishmentDate);
                    punishments.add(punishment);
                }
                return punishments;
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
            return punishments;
        }
    }

    public static boolean insertAward(@NotNull Award award) {
        String studentID = award.getStudentID();
        String awardName = award.getAwardName();
        String awardLevel = award.getAwardLevel();
        Date awardDate = award.getAwardDate();
        String sql;
        boolean success = false;
        if (awardDate == null) {
            sql = "INSERT INTO Award(StudentID, AwardName, AwardLevel) VALUES (?, ?, ?)";
            try (
                Connection conn = DBConnection.getConnection(true);
                PreparedStatement ps = conn.prepareStatement(sql)
            ) {
                ps.setString(1, studentID);
                ps.setString(2, awardName);
                ps.setString(3, awardLevel);
                success = ps.executeUpdate() == 1;
            } catch (SQLException e) {
                DBConnection.SQLExceptionHandler(e);
                return false;
            }
        } else {
            sql = "INSERT INTO Award(StudentID, AwardName, AwardLevel, AwardDate) VALUES (?, ?, ?, ?)";
            try (
                Connection conn = DBConnection.getConnection(true);
                PreparedStatement ps = conn.prepareStatement(sql)
            ) {
                ps.setString(1, studentID);
                ps.setString(2, awardName);
                ps.setString(3, awardLevel);
                ps.setDate(4, awardDate);
                success = ps.executeUpdate() == 1;
            } catch (SQLException e) {
                DBConnection.SQLExceptionHandler(e);
                return false;
            }
        }
        return success;
    }

    public static boolean insertPunishment(@NotNull Punishment punishment) {
        String studentID = punishment.getStudentID();
        String punishmentName = punishment.getPunishmentName();
        Date punishmentDate = punishment.getPunishmentDate();
        String sql;
        boolean success = false;
        if (punishmentDate == null) {
            sql = "INSERT INTO Punishment(StudentID, PunishmentName) VALUES (?, ?)";
            try (
                Connection conn = DBConnection.getConnection(true);
                PreparedStatement ps = conn.prepareStatement(sql)
            ) {
                ps.setString(1, studentID);
                ps.setString(2, punishmentName);
                success = ps.executeUpdate() == 1;
            } catch (SQLException e) {
                DBConnection.SQLExceptionHandler(e);
                return false;
            }
        } else {
            sql = "INSERT INTO Punishment(StudentID, PunishmentName, PunishmentDate) VALUES (?, ?, ?)";
            try (
                Connection conn = DBConnection.getConnection(true);
                PreparedStatement ps = conn.prepareStatement(sql)
            ) {
                ps.setString(1, studentID);
                ps.setString(2, punishmentName);
                ps.setDate(3, punishmentDate);
                success = ps.executeUpdate() == 1;
            } catch (SQLException e) {
                DBConnection.SQLExceptionHandler(e);
                return false;
            }
        }
        return success;
    }

    // update the award: returns true if at least one field is updated
    public static boolean updateAward(String studentID, String oldAwardName, String newAwardName, String newAwardLevel, Date newAwardDate) {
        // check the primary keys
        if (DAO.isAnyStringEmpty(studentID, oldAwardName)) {
            return false;
        }
        // check if the award exists
        List<Award> awards = queryAwards(studentID);
        boolean awardExists = false;
        for (Award award : awards) {
            if (award.getAwardName().equals(oldAwardName)) {
                awardExists = true;
                break;
            }
        }
        if (!awardExists) {
            return false;
        }
        // update the award
        // check if there is a change
        if (DAO.areAllStringsEmpty(newAwardName, newAwardLevel) && newAwardDate == null) {
            return false;
        }
        // create a new connection and disable auto-commit
        try (
            Connection conn = DBConnection.getConnection(true)
        ) {
            // disable auto-commit
            conn.setAutoCommit(false);
            // update the award fields
            if (!DAO.isStringEmpty(newAwardLevel)) {
                if (!updateAwardField("AwardLevel", newAwardLevel, "StudentID", studentID, "AwardName", oldAwardName, conn)) {
                    conn.rollback();
                    return false;
                }
            }
            if (newAwardDate != null) {
                if (!updateAwardField("AwardDate", newAwardDate, "StudentID", studentID,"AwardName", oldAwardName, conn)) {
                    conn.rollback();
                    return false;
                }
            }
            if (!DAO.isStringEmpty(newAwardName)) {
                if (!updateAwardField("AwardName", newAwardName, "StudentID", studentID, "AwardName", oldAwardName, conn)) {
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

    // update the punishment: returns true if at least one field is updated
    public static boolean updatePunishment(String studentID, String oldPunishmentName, String newPunishmentName, Date newPunishmentDate) {
        // check the primary keys
        if (DAO.isAnyStringEmpty(studentID, oldPunishmentName)) {
            return false;
        }
        // check if the punishment exists
        List<Punishment> punishments = queryPunishments(studentID);
        boolean punishmentExists = false;
        for (Punishment punishment : punishments) {
            if (punishment.getPunishmentName().equals(oldPunishmentName)) {
                punishmentExists = true;
                break;
            }
        }
        if (!punishmentExists) {
            return false;
        }
        // update the punishment
        // check if there is a change
        if (DAO.isStringEmpty(newPunishmentName) && newPunishmentDate == null) {
            return false;
        }
        // create a new connection and disable auto-commit
        try (
            Connection conn = DBConnection.getConnection(true)
        ) {
            // disable auto-commit
            conn.setAutoCommit(false);
            // update the punishment fields
            if (newPunishmentDate != null) {
                if (!updatePunishmentField("PunishmentDate", newPunishmentDate, "StudentID", studentID, "PunishmentName", oldPunishmentName, conn)) {
                    conn.rollback();
                    return false;
                }
            }
            if (!DAO.isStringEmpty(newPunishmentName)) {
                if (!updatePunishmentField("PunishmentName", newPunishmentName, "StudentID", studentID, "PunishmentName", oldPunishmentName, conn)) {
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

    // delete the award: returns true if the award is deleted
    public static boolean deleteAward(String studentID, String awardName) {
        // check the primary keys
        if (DAO.isAnyStringEmpty(studentID, awardName)) {
            return false;
        }
        return deleteFromAward(studentID, awardName);
    }

    // delete the punishment: returns true if the punishment is deleted
    public static boolean deletePunishment(String studentID, String punishmentName) {
        // check the primary keys
        if (DAO.isAnyStringEmpty(studentID, punishmentName)) {
            return false;
        }
        return deleteFromPunishment(studentID, punishmentName);
    }

    public static void main(String[] args) {
        String studentID = "PB21111738";
        List<Award> awards = queryAwards(studentID);
        for (Award award : awards) {
            System.out.println(award.toString());
        }
        List<Punishment> punishments = queryPunishments(studentID);
        for (Punishment punishment : punishments) {
            System.out.println(punishment.toString());
        }
    }
}
