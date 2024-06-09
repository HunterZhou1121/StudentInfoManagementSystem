package cn.edu.ustc.studentinfomanagementsystem.models;

public class Course {
    protected String courseName;
    protected String courseID;
    protected String credits;

    public Course(String courseName, String courseID, String credits) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.credits = credits;
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

    @Override
    public String toString() {
        return courseName + ", " + courseID + ", " + credits;
    }
}
