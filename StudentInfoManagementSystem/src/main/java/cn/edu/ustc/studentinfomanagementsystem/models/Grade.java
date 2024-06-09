package cn.edu.ustc.studentinfomanagementsystem.models;

public class Grade extends Course {
    private String studentID;

    private Integer score;

    private String status;

    public Grade(
        String studentID,
        String courseName,
        String courseID,
        String credits,
        Integer score,
        String status
    ) {
        super(courseName, courseID, credits);
        this.studentID = studentID;
        this.score = score;
        this.status = status;
    }


    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return studentID + ", " + courseName + ", " + courseID + ", " + credits + ", " + score + ", " + status;
    }
}
