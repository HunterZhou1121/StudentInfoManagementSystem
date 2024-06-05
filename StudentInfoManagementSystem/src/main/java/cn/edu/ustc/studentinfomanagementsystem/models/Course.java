package cn.edu.ustc.studentinfomanagementsystem.models;

public class Course {
    private String studentID;

    private String courseName;

    private String courseID;

    private String credits;

    private Integer score;

    private String status;

    public Course(
        String studentID,
        String courseName,
        String courseID,
        String credits,
        Integer score,
        String status
    ) {
        this.studentID = studentID;
        this.courseName = courseName;
        this.courseID = courseID;
        this.credits = credits;
        this.score = score;
        this.status = status;
    }


    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
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
