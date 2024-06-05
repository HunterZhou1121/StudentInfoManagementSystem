package cn.edu.ustc.studentinfomanagementsystem.models;

import java.sql.*;

public class Student {
    // ID, PhotoURL, Name, Gender, DOB, Ethnicity, PoliticalAffiliation,
    // PhoneNumber, Email, StudentID, EnrolmentDate, Major
    private String ID;
    private String photoURL;
    private String name;
    private String gender;
    private Date DOB;
    private String ethnicity;
    private String politicalAffiliation;
    private String phoneNumber;
    private String email;
    private String studentID;
    private Date enrolmentDate;
    private String major;

    // constructor: PhotoURL, PhoneNumber and Email are not required
    public Student(
            String ID,
            String name,
            String gender,
            Date DOB,
            String ethnicity,
            String politicalAffiliation,
            String studentID,
            Date enrolmentDate,
            String major
    ) {
        this.ID = ID;
        this.name = name;
        this.gender = gender;
        this.DOB = DOB;
        this.ethnicity = ethnicity;
        this.politicalAffiliation = politicalAffiliation;
        this.studentID = studentID;
        this.enrolmentDate = enrolmentDate;
        this.major = major;
    }

    // setters
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public void setPoliticalAffiliation(String politicalAffiliation) {
        this.politicalAffiliation = politicalAffiliation;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setEnrolmentDate(Date enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    // getters
    public String getID() {
        return ID;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Date getDOB() {
        return DOB;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public String getPoliticalAffiliation() {
        return politicalAffiliation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentID() {
        return studentID;
    }

    public Date getEnrolmentDate() {
        return enrolmentDate;
    }

    public String getMajor() {
        return major;
    }

    @Override
    public String toString() {
        return ID + ", " + photoURL + ", " + name + ", " + gender + ", " +
                DOB.toString() + ", " + ethnicity + ", " + politicalAffiliation + ", " +
                phoneNumber + ", " + email + ", " + studentID + ", " + enrolmentDate.toString() + ", " + major;
    }




}
