package cn.edu.ustc.studentinfomanagementsystem.controllers;

import cn.edu.ustc.studentinfomanagementsystem.DAO.AwardPunishmentDAO;
import cn.edu.ustc.studentinfomanagementsystem.DAO.CourseDAO;
import cn.edu.ustc.studentinfomanagementsystem.DAO.StudentDAO;
import cn.edu.ustc.studentinfomanagementsystem.models.*;
import cn.edu.ustc.studentinfomanagementsystem.utils.ImageUploader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import cn.edu.ustc.studentinfomanagementsystem.SceneManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Date;

public class StudentController extends Controller {
    private Student student;

    @FXML private Label welcomeLabel;

    @FXML private Button logoutButton;

    @FXML private Button refreshButton;

    @FXML private TabPane studentTabPane;

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

    @FXML private TableView<Award> awardTableView;

    @FXML private TableColumn<Award, String> awardNameColumn;

    @FXML private TableColumn<Award, String> awardLevelColumn;

    @FXML private TableColumn<Award, Date> awardDateColumn;

    @FXML private TableView<Punishment> punishmentTableView;

    @FXML private TableColumn<Punishment, String> punishmentNameColumn;

    @FXML private TableColumn<Punishment, Date> punishmentDateColumn;

    @FXML private TableView<Course> selectedCourseTableView;

    @FXML private TableColumn<Course, String> selectedCourseNameColumn;

    @FXML private TableColumn<Course, String> selectedCourseIDColumn;

    @FXML private TableColumn<Course, String> selectedCourseCreditsColumn;

    @FXML private TableView<Course> gradeTableView;

    @FXML private TableColumn<Course, String> gradeCourseNameColumn;

    @FXML private TableColumn<Course, String> gradeCourseIDColumn;

    @FXML private TableColumn<Course, String> gradeCreditsColumn;

    @FXML private TableColumn<Course, Integer> gradeColumn;

    @FXML private TableColumn<Course, String> gradeStatusColumn;

    @FXML private TextField passedCreditsTextField;

    @FXML private TextField failedCourseNumberTextField;

    @FXML private TextField failedCreditsTextField;

    @FXML private TextField weightedAverageScoreTextField;

    private boolean welcomeLabelLoaded = false;

    private boolean basicInfoLoaded = false;

    private boolean awardsLoaded = false;

    private boolean punishmentsLoaded = false;

    private boolean selectedCoursesLoaded = false;

    private boolean gradesLoaded = false;

    private boolean statisticsLoaded = false;


    @FXML
    public void initialize() {
        // set the columns of the awardTableView
        awardNameColumn.setCellValueFactory(new PropertyValueFactory<>("awardName"));
        awardLevelColumn.setCellValueFactory(new PropertyValueFactory<>("awardLevel"));
        awardDateColumn.setCellValueFactory(new PropertyValueFactory<>("awardDate"));

        // set the columns of the punishmentTableView
        punishmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("punishmentName"));
        punishmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("punishmentDate"));

        // set the columns of the selectedCourseTableView
        selectedCourseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        selectedCourseIDColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        selectedCourseCreditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        // set the columns of the gradeTableView
        gradeCourseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        gradeCourseIDColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        gradeCreditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        gradeStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // create a listener to load awards and punishments when the tab is selected
        studentTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getText()) {
                case "基本信息" -> {
                    // load basic information
                    loadBasicInfo();
                    // load the welcome label
                    loadWelcomeLabel();

                }
                case "奖惩情况" -> {
                    loadAwards();
                    loadPunishments();
                }
                case "我的课程" -> loadSelectedCourses();
                case "我的成绩" -> {
                    loadGrades();
                    loadStatistics();
                }

            }
        });
    }

    @FXML
    private void handleLogoutButtonAction() {
        // reset the student
        setStudent();
        // reset all flags
        resetFlags();
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
        if (StudentDAO.updateStudentPhoneNumber(newPhoneNumber, student.getID())) {
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
        if (StudentDAO.updateStudentEmail(newEmail, student.getID())) {
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
        String newPhotoURL = ImageUploader.uploadImage(file);
        if (newPhotoURL == null) {
            showAlert("上传失败", "照片上传失败！");
            return;
        }
        student.setPhotoURL(newPhotoURL);
        if (StudentDAO.updateStudentPhotoURL(newPhotoURL, student.getID())) {
            loadImageView();
            showAlert("更新成功", "照片更新成功！");
        } else {
            student.setPhotoURL(oldPhotoURL);
            showAlert("更新失败", "照片更新失败！");
        }

    }

    @FXML
    private void refreshAllInfo() {
        resetFlags();
        setStudent(student.getStudentID());
        loadBasicInfo();
        loadAwards();
        loadPunishments();
        loadSelectedCourses();
        loadGrades();
        loadStatistics();
    }

    // set student queried from the database with studentID
    public void setStudent(@NotNull String studentID) {
        this.student = StudentDAO.queryStudent(studentID);
    }

    // set student to null
    public void setStudent() {
        this.student = null;
    }

    // reset all flags
    public void resetFlags() {
        welcomeLabelLoaded = false;
        basicInfoLoaded = false;
        awardsLoaded = false;
        punishmentsLoaded = false;
        selectedCoursesLoaded = false;
        gradesLoaded = false;
        statisticsLoaded = false;
    }

    public void loadWelcomeLabel() {
        if (student == null || welcomeLabelLoaded) {
            return;
        }
        setText(welcomeLabel, "欢迎，" + student.getName() + "！");
        welcomeLabelLoaded = true;
    }

    public void loadBasicInfo() {
        if (student == null || basicInfoLoaded) {
            return;
        }
        loadImageView();
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
        basicInfoLoaded = true;
    }

    public void loadBasicInfo(boolean force) {
        if (force) {
            basicInfoLoaded = false;
        }
        loadBasicInfo();
    }

    public void loadImageView() {
        String photoURL = student.getPhotoURL();
        if (photoURL == null) {
            imageView.setImage(null);
            return;
        }
        setImage(imageView, photoURL);
    }

    public void loadAwards() {
        if (student == null || awardsLoaded) {
            return;
        }
        awardTableView.setItems(FXCollections.observableArrayList(AwardPunishmentDAO.queryAwards(student.getStudentID())));
        awardsLoaded = true;
    }

    public void loadAwards(boolean force) {
        if (force) {
            awardsLoaded = false;
        }
        loadAwards();
    }

    public void loadPunishments() {
        if (student == null || punishmentsLoaded) {
            return;
        }
        punishmentTableView.setItems(FXCollections.observableArrayList(AwardPunishmentDAO.queryPunishments(student.getStudentID())));
        punishmentsLoaded = true;
    }

    public void loadPunishments(boolean force) {
        if (force) {
            punishmentsLoaded = false;
        }
        loadPunishments();
    }

    public void loadSelectedCourses() {
        if (student == null || selectedCoursesLoaded) {
            return;
        }
        selectedCourseTableView.setItems(FXCollections.observableArrayList(CourseDAO.querySelectedCourses(student.getStudentID())));
        selectedCoursesLoaded = true;
    }

    public void loadSelectedCourses(boolean force) {
        if (force) {
            selectedCoursesLoaded = false;
        }
        loadSelectedCourses();
    }

    public void loadGrades() {
        if (student == null || gradesLoaded) {
            return;
        }
        gradeTableView.setItems(FXCollections.observableArrayList(CourseDAO.queryGrades(student.getStudentID())));
        gradesLoaded = true;
    }

    public void loadGrades(boolean force) {
        if (force) {
            gradesLoaded = false;
        }
        loadGrades();
    }

    public void loadPassedCredits() {
        if (student == null) {
            return;
        }
        setText(passedCreditsTextField, CourseDAO.getPassedCredits(student.getStudentID()));
    }

    public void loadFailedCourseNumber() {
        if (student == null) {
            return;
        }
        Integer failedCourseNum = CourseDAO.getFailedCourseNum(student.getStudentID());
        if (failedCourseNum == null) {
            clearText(failedCourseNumberTextField);
            return;
        }
        setText(failedCourseNumberTextField, failedCourseNum.toString());
    }

    public void loadFailedCredits() {
        if (student == null) {
            return;
        }
        setText(failedCreditsTextField, CourseDAO.getFailedCredits(student.getStudentID()));
    }

    public void loadWeightedAverageScore() {
        if (student == null) {
            return;
        }
        // weighted average score can be null when there are no scores
        Float weightedAverageScore = CourseDAO.getWeightedAverageScore(student.getStudentID());
        if (weightedAverageScore == null) {
            clearText(weightedAverageScoreTextField);
            return;
        }
        setText(weightedAverageScoreTextField, weightedAverageScore.toString());
    }

    public void loadStatistics() {
        if (student == null || statisticsLoaded) {
            return;
        }
        loadPassedCredits();
        loadFailedCourseNumber();
        loadFailedCredits();
        loadWeightedAverageScore();
        statisticsLoaded = true;
    }

    public void loadStatistics(boolean force) {
        if (force) {
            statisticsLoaded = false;
        }
        loadStatistics();
    }

}
