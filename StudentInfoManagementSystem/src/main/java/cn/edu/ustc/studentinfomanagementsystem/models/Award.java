package cn.edu.ustc.studentinfomanagementsystem.models;

import java.sql.Date;

public class Award {
    private String studentID;

    private String awardName;

    private String awardLevel;

    private Date awardDate;

    public Award(String studentID, String awardName, String awardLevel, Date awardDate) {
        this.studentID = studentID;
        this.awardName = awardName;
        this.awardLevel = awardLevel;
        this.awardDate = awardDate;
    }

    // default date: curdate()
    public Award(String studentID, String awardName, String awardLevel) {
        this.studentID = studentID;
        this.awardName = awardName;
        this.awardLevel = awardLevel;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardLevel() {
        return awardLevel;
    }

    public void setAwardLevel(String awardLevel) {
        this.awardLevel = awardLevel;
    }

    public Date getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    @Override
    public String toString() {
        // StudentID, AwardName, AwardLevel, AwardDate
        return studentID + ", " + awardName + ", " + awardLevel + ", " + awardDate;
    }
}
