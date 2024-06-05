package cn.edu.ustc.studentinfomanagementsystem.controllers;

import cn.edu.ustc.studentinfomanagementsystem.DAO.StudentDAO;
import cn.edu.ustc.studentinfomanagementsystem.models.Student;
import cn.edu.ustc.studentinfomanagementsystem.utils.imageUploader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import cn.edu.ustc.studentinfomanagementsystem.SceneManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class StudentController extends Controller {
    private Student student;

    private final StudentDAO studentDAO = new StudentDAO();
//    private String StudentID;
//
//    private String StudentName;
//
//    private String ID;
//
//    private String PhotoURL;
//
//    private String Gender;
//
//    private Date DOB;
//
//    private String Ethnicity;
//
//    private String PoliticalAffiliation;

    @FXML private Label welcomeLabel;

    @FXML private Button logoutButton;

    @FXML private ImageView imageView;

    @FXML private TextField nameTextField;

    @FXML private TextField IDTextField;

    @FXML private TextField genderTextField;

    @FXML private TextField DOBTextField;

    @FXML private TextField ethnicityTextField;

    @FXML private TextField politicalAffiliationTextField;

    @FXML private TextField phoneNumberTextField;

    @FXML private TextField emailTextField;

    @FXML private TextField studentIDTextField;

    @FXML private TextField enrolmentDateTextField;

    @FXML private TextField majorTextField;

    @FXML
    private void handleLogoutButtonAction() {
        // reset the student
        setStudent();
        // switch to the login view
        SceneManager.getInstance().switchScene("login-view");
        // delete the student view
        SceneManager.getInstance().clearScene("student-view");
        // alert: "登出成功"
        showAlert("登出成功", "登出成功！");
    }
    @FXML
    private void updatePhoneNumber() {
        if (student == null) {
            return;
        }
        String oldPhoneNumber = student.getPhoneNumber();
        String newPhoneNumber = phoneNumberTextField.getText();
        student.setPhoneNumber(newPhoneNumber);
        if (studentDAO.updateStudentPhoneNumber(newPhoneNumber, student.getID())) {
            showAlert("更新成功", "手机号码更新成功！");
        } else {
            student.setPhoneNumber(oldPhoneNumber);
//            phoneNumberTextField.setText(oldPhoneNumber);
            setText(phoneNumberTextField, oldPhoneNumber);
            showAlert("更新失败", "手机号码更新失败！");
        }
    }

    @FXML
    private void updateEmail() {
        if (student == null) {
            return;
        }
        String oldEmail = student.getEmail();
        String newEmail = emailTextField.getText();
        student.setEmail(newEmail);
        if (studentDAO.updateStudentEmail(newEmail, student.getID())) {
            showAlert("更新成功", "电子邮箱更新成功！");
        } else {
            student.setEmail(oldEmail);
//            emailTextField.setText(oldEmail);
            setText(emailTextField, oldEmail);
            showAlert("更新失败", "电子邮箱更新失败！");
        }
    }

    @FXML
    private void updatePhoto() {
        if (student == null) {
            return;
        }
        String oldPhotoURL = student.getPhotoURL();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setTitle("选择照片");
        File file = fileChooser.showOpenDialog(SceneManager.getInstance().getStage());
        if (file == null) {
            // operation cancelled
            return;
        }
        // upload using imageUploader
        String newPhotoURL = imageUploader.uploadImage(file);
        if (newPhotoURL == null) {
            showAlert("上传失败", "照片上传失败！");
            return;
        }
        student.setPhotoURL(newPhotoURL);
        if (studentDAO.updateStudentPhotoURL(newPhotoURL, student.getID())) {
            setImageView();
            showAlert("更新成功", "照片更新成功！");
        } else {
            student.setPhotoURL(oldPhotoURL);
            showAlert("更新失败", "照片更新失败！");
        }

    }

    // set student queried from the database with studentID
    public void setStudent(@NotNull String studentID) {
        this.student = studentDAO.queryStudent(studentID);
    }

    // set student to null
    public void setStudent() {
        this.student = null;
    }

    public void updateFields() {
        if (student == null) {
            return;
        }
        setImageView();
        setImage(imageView, student.getPhotoURL());
        setText(nameTextField, student.getName());
        setText(IDTextField, student.getID());
        setText(genderTextField, student.getGender());
        setText(DOBTextField, student.getDOB().toString());
        setText(ethnicityTextField, student.getEthnicity());
        setText(politicalAffiliationTextField, student.getPoliticalAffiliation());
        setText(phoneNumberTextField, student.getPhoneNumber());
        setText(emailTextField, student.getEmail());
        setText(studentIDTextField, student.getStudentID());
        setText(enrolmentDateTextField, student.getEnrolmentDate().toString());
        setText(majorTextField, student.getMajor());
    }

    public void setImageView() {
        String photoURL = student.getPhotoURL();
        if (photoURL == null) {
            return;
        }
//        Image image = new Image(photoURL);
//        imageView.setImage(image);
        setImage(imageView, photoURL);
    }

}
