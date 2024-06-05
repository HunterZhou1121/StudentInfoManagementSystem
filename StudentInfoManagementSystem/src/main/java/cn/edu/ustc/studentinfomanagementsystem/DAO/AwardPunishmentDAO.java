package cn.edu.ustc.studentinfomanagementsystem.DAO;

import cn.edu.ustc.studentinfomanagementsystem.models.Punishment;
import cn.edu.ustc.studentinfomanagementsystem.utils.DBConnection;
import cn.edu.ustc.studentinfomanagementsystem.models.Award;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AwardPunishmentDAO {
    private Connection connection;

    public AwardPunishmentDAO() {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
        }
    }

    public List<Award> queryAwards(String studentID) {
        String sql = "SELECT * FROM Award WHERE StudentID = ?";
        List<Award> awards = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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

    public List<Punishment> queryPunishments(String studentID) {
        String sql = "SELECT * FROM Punishment WHERE StudentID = ?";
        List<Punishment> punishments = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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

    public static void main(String[] args) {
        AwardPunishmentDAO awardPunishmentDAO = new AwardPunishmentDAO();
        String studentID = "PB21111738";
        List<Award> awards = awardPunishmentDAO.queryAwards(studentID);
        for (Award award : awards) {
            System.out.println(award.toString());
        }
        List<Punishment> punishments = awardPunishmentDAO.queryPunishments(studentID);
        for (Punishment punishment : punishments) {
            System.out.println(punishment.toString());
        }
    }
}
