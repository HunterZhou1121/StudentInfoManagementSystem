package cn.edu.ustc.studentinfomanagementsystem.models;

import java.sql.Date;

public class Punishment {
    private String studentID;

    private String punishmentName;

    private Date punishmentDate;

    public Punishment(String studentID, String punishmentName, Date punishmentDate) {
        this.studentID = studentID;
        this.punishmentName = punishmentName;
        this.punishmentDate = punishmentDate;
    }

    // default date: curdate()
    public Punishment(String studentID, String punishmentName) {
        this.studentID = studentID;
        this.punishmentName = punishmentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getPunishmentName() {
        return punishmentName;
    }

    public void setPunishmentName(String punishmentName) {
        this.punishmentName = punishmentName;
    }

    public Date getPunishmentDate() {
        return punishmentDate;
    }

    public void setPunishmentDate(Date punishmentDate) {
        this.punishmentDate = punishmentDate;
    }

    @Override
    public String toString() {
        // StudentID, AwardName, AwardLevel, AwardDate
        return studentID + ", " + punishmentName + ", " + punishmentDate;
    }
}
