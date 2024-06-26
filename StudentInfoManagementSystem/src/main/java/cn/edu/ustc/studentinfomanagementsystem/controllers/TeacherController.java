package cn.edu.ustc.studentinfomanagementsystem.controllers;

import cn.edu.ustc.studentinfomanagementsystem.DAO.AwardPunishmentDAO;
import cn.edu.ustc.studentinfomanagementsystem.DAO.CourseDAO;
import cn.edu.ustc.studentinfomanagementsystem.DAO.GradeDAO;
import cn.edu.ustc.studentinfomanagementsystem.DAO.StudentDAO;
import cn.edu.ustc.studentinfomanagementsystem.SceneManager;
import cn.edu.ustc.studentinfomanagementsystem.models.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TeacherController extends Controller {

    private static final List<String> GENDERS = List.of("男", "女");

    private static final List<String> ETHNICITIES = List.of(
        "汉族",
        "蒙古族",
        "回族",
        "藏族",
        "维吾尔族",
        "苗族",
        "彝族",
        "壮族",
        "布依族",
        "朝鲜族",
        "满族",
        "侗族",
        "瑶族",
        "白族",
        "土家族",
        "哈尼族",
        "哈萨克族",
        "傣族",
        "黎族",
        "傈僳族",
        "佤族",
        "畲族",
        "高山族",
        "拉祜族",
        "水族",
        "东乡族",
        "纳西族",
        "景颇族",
        "柯尔克孜族",
        "土族",
        "达斡尔族",
        "仫佬族",
        "羌族",
        "布朗族",
        "撒拉族",
        "毛南族",
        "仡佬族",
        "锡伯族",
        "阿昌族",
        "普米族",
        "塔吉克族",
        "怒族",
        "乌孜别克族",
        "俄罗斯族",
        "鄂温克族",
        "德昂族",
        "保安族",
        "裕固族",
        "京族",
        "塔塔尔族",
        "独龙族",
        "鄂伦春族",
        "赫哲族",
        "门巴族",
        "珞巴族",
        "基诺族"
    );

    private static final List<String> POLITICAL_AFFILIATIONS = List.of(
        "中共党员",
        "中共预备党员",
        "共青团员",
        "民革会员",
        "民盟盟员",
        "民建会员",
        "民进会员",
        "农工党党员",
        "致公党党员",
        "九三学社社员",
        "台盟盟员",
        "无党派民主人士",
        "群众"
    );

    private static final List<String> MAJORS = List.of(
        "数学与应用数学",
        "信息与计算科学",
        "物理学",
        "应用物理学",
        "天文学",
        "光电信息科学与工程",
        "工商管理",
        "信息管理与信息系统",
        "管理科学",
        "金融学",
        "统计学",
        "化学",
        "材料物理",
        "材料化学",
        "高分子材料与工程",
        "地球物理学",
        "大气科学",
        "空间科学与技术",
        "地球化学",
        "行星科学",
        "环境科学",
        "理论与应用力学",
        "机械设计制造及其自动化",
        "测控技术与仪器",
        "能源与动力工程",
        "安全工程",
        "英语",
        "考古学",
        "传播学",
        "网络与新媒体",
        "核工程与核技术",
        "工程物理",
        "应用物理学",
        "环境科学与工程",
        "量子信息科学",
        "生物科学",
        "生物技术",
        "临床医学",
        "电子信息工程",
        "通信工程",
        "自动化",
        "人工智能",
        "计算机科学与技术",
        "软件工程",
        "电子科学与技术",
        "信息安全",
        "网络空间安全",
        "数据科学与大数据技术"
    );

    private static final List<String> AWARD_LEVELS = List.of(
        "院级", "校级", "市级", "省级", "国家级"
    );

    private String teacherUsername;

    private Student currentStudent;

    @FXML private Label welcomeLabel;

    // the first level
    @FXML private TabPane teacherTabPane;

    // the second level
    @FXML private TabPane teacherStudentInfoTabPane;

    // query student info

    @FXML private ComboBox<String> studentIDComboBox;

    @FXML private ComboBox<String> genderComboBox;

    @FXML private ComboBox<String> ethnicityComboBox;

    @FXML private ComboBox<String> politicalAffiliationComboBox;

    @FXML private ComboBox<String> majorComboBox;

//    @FXML private DatePicker enrolmentDatePicker;

    @FXML private TextField enrolmentDateTextField;

    @FXML private ImageView photoImageView;

    @FXML private TextField nameTextField;

    @FXML private TextField IDTextField;

    @FXML private TextField DOBTextField;

    @FXML private TextField phoneNumberTextField;

    @FXML private TextField emailTextField;

    @FXML private TextField studentIDTextField;

    // add new student

    @FXML private TextField newNameTextField;

    @FXML private TextField newIDTextField;

    @FXML private ComboBox<String> newGenderComboBox;

    @FXML private DatePicker newDOBDatePicker;

    @FXML private ComboBox<String> newEthnicityComboBox;

    @FXML private ComboBox<String> newPoliticalAffiliationComboBox;

    @FXML private TextField newStudentIDTextField;

    @FXML private DatePicker newEnrolmentDatePicker;

    @FXML private ComboBox<String> newMajorComboBox;

    // the second level
    @FXML private TabPane teacherAwardPunishmentTabPane;

    // query
    // award
    @FXML private TableView<Award> awardTableView;

    @FXML private TableColumn<Award, String> awardNameTableColumn;

    @FXML private TableColumn<Award, String> awardLevelTableColumn;

    @FXML private TableColumn<Award, Date> awardDateTableColumn;

    // punishment
    @FXML private TableView<Punishment> punishmentTableView;

    @FXML private TableColumn<Punishment, String> punishmentNameTableColumn;

    @FXML private TableColumn<Punishment, Date> punishmentDateTableColumn;

    // add
    // award
    @FXML private TextField newAwardStudentIDTextField;

    @FXML private TextField newAwardNameTextField;

    @FXML private ComboBox<String> newAwardLevelComboBox;

    @FXML private DatePicker newAwardDatePicker;

    // punishment
    @FXML private TextField newPunishmentStudentIDTextField;

    @FXML private TextField newPunishmentNameTextField;

    @FXML private DatePicker newPunishmentDatePicker;

    // update
    // award
    @FXML private TextField updateAwardStudentIDTextField;

    @FXML private TextField updateAwardOldNameTextField;

    @FXML private TextField updateAwardNewNameTextField;

    @FXML private ComboBox<String> updateAwardNewLevelComboBox;

    @FXML private DatePicker updateAwardNewDatePicker;
    // punishment
    @FXML private TextField updatePunishmentStudentIDTextField;

    @FXML private TextField updatePunishmentOldNameTextField;

    @FXML private TextField updatePunishmentNewNameTextField;

    @FXML private DatePicker updatePunishmentNewDatePicker;

    // delete
    // award
    @FXML private TextField deleteAwardStudentIDTextField;

    @FXML private TextField deleteAwardNameTextField;

    // punishment
    @FXML private TextField deletePunishmentStudentIDTextField;

    @FXML private TextField deletePunishmentNameTextField;

    // course
    // the second level tab pane
    @FXML private TabPane teacherCourseTabPane;

    // query
    @FXML private TableView<Course> courseTableView;

    @FXML private TableColumn<Course, String> courseNameTableColumn;

    @FXML private TableColumn<Course, String> courseIDTableColumn;

    @FXML private TableColumn<Course, String> courseCreditsTableColumn;

    // add
    @FXML private TextField newCourseIDTextField;

    @FXML private TextField newCourseNameTextField;

    @FXML private TextField newCourseCreditsTextField;

    // update
    @FXML private TextField updateOldCourseIDTextField;

    @FXML private TextField updateNewCourseIDTextField;

    @FXML private TextField updateNewCourseNameTextField;

    @FXML private TextField updateNewCourseCreditsTextField;

    // delete
    @FXML private TextField deleteCourseIDTextField;

    // grade
    // the second level tab pane
    @FXML private TabPane teacherGradeTabPane;
    // student courses
    @FXML private TableView<Grade> studentCourseTableView;

    @FXML private TableColumn<Grade, String> studentCourseStudentIDTableColumn;

    @FXML private TableColumn<Grade, String> studentCourseCourseNameTableColumn;

    @FXML private TableColumn<Grade, String> studentCourseCourseIDTableColumn;

    @FXML private TableColumn<Grade, String> studentCourseCreditsTableColumn;

    // student grades
    @FXML private TableView<Grade> studentGradeTableView;

    @FXML private TableColumn<Grade, String> studentGradeStudentIDTableColumn;

    @FXML private TableColumn<Grade, String> studentGradeCourseNameTableColumn;

    @FXML private TableColumn<Grade, String> studentGradeCourseIDTableColumn;

    @FXML private TableColumn<Grade, String> studentGradeCreditsTableColumn;

    @FXML private TableColumn<Grade, String> studentGradeGradeTableColumn;

    @FXML private TableColumn<Grade, String> studentGradeStatusTableColumn;

    @FXML private TextField studentGradePassedCreditsTextField;

    @FXML private TextField studentGradeFailedCourseNumberTextField;

    @FXML private TextField studentGradeFailedCreditsTextField;

    @FXML private TextField studentGradeWeightedAverageScoreTextField;

    // add course for student
    @FXML private TextField addCourseStudentIDTextField;

    @FXML private TextField addCourseCourseIDTextField;

    // update course grade
    @FXML private TextField updateCourseGradeStudentIDTextField;

    @FXML private TextField updateCourseGradeCourseIDTextField;

    @FXML private TextField updateCourseGradeNewGradeTextField;

    // delete course for student
    @FXML private TextField deleteCourseStudentIDTextField;

    @FXML private TextField deleteCourseCourseIDTextField;




    private boolean welcomeLabelLoaded = false;

    private boolean studentQueryComboBoxesLoaded = false;

    private boolean studentAddComboBoxesLoaded = false;

    private boolean studentIDComboBoxLoaded = false;

    private boolean awardsLoaded = false;

    private boolean punishmentsLoaded = false;

    private boolean awardLevelComboBoxesLoaded = false;

    private boolean coursesLoaded = false;

    private boolean studentCoursesLoaded = false;

    private boolean studentGradesLoaded = false;

    private boolean studentGradeStatisticsLoaded = false;

    @FXML
    private void initialize() {
        // set the columns for tables
        // award
        awardNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("awardName"));
        awardLevelTableColumn.setCellValueFactory(new PropertyValueFactory<>("awardLevel"));
        awardDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("awardDate"));
        // punishment
        punishmentNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("punishmentName"));
        punishmentDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("punishmentDate"));
        // course
        courseNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        courseCreditsTableColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        // grade
        studentCourseStudentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        studentCourseCourseNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        studentCourseCourseIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        studentCourseCreditsTableColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        studentGradeStudentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        studentGradeCourseNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        studentGradeCourseIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        studentGradeCreditsTableColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        studentGradeGradeTableColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        studentGradeStatusTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // initialize the first ComboBox
        initializeStudentQueryComboBoxes();
        // listeners for the 1st level tab pane
        teacherTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getText()) {
                case "学生信息" -> {
                    // consider 2nd level tab pane
                    String subTabName = teacherStudentInfoTabPane.getSelectionModel().selectedItemProperty().getValue().getText();
                    switch (subTabName) {
                        case "查询" -> {
                            // load studentIDs
                            loadStudentIDComboBox(false);
                            initializeStudentQueryComboBoxes();
                        }
                        case "添加" -> {
                            // load the ComboBoxes
                            initializeStudentAddComboBoxes();
                        }
                    }
                }
                case "奖惩情况" -> {
                    // consider 2nd level tab pane
                    String subTabName = teacherAwardPunishmentTabPane.getSelectionModel().selectedItemProperty().getValue().getText();
                    switch (subTabName) {
                        case "查询" -> {
                            // load award and punishment table views
                            loadAwards();
                            loadPunishments();
                        }
                        case "添加" -> {
                            initializeAwardLevelComboBoxes();
                        }
                        case "修改" -> {
                            initializeAwardLevelComboBoxes();
                        }
                        case "删除" -> {

                        }
                    }
                }
                case "课程管理" -> {
                    // consider 2nd level tab pane
                    String subTabName = teacherCourseTabPane.getSelectionModel().selectedItemProperty().getValue().getText();
                    switch (subTabName) {
                        case "查询" -> {
                            loadCourses();
                        }
                        case "添加" -> {

                        }
                        case "修改" -> {

                        }
                        case "删除" -> {

                        }
                    }
                }
                case "成绩管理" -> {
                    // consider 2nd level tab pane
                    String subTabName = teacherGradeTabPane.getSelectionModel().selectedItemProperty().getValue().getText();
                    switch (subTabName) {
                        case "学生选课查询" -> {
                            loadStudentCurrentCourses();
                        }
                        case "学生成绩查询" -> {
                            loadStudentGradeStatistics();
                            loadStudentGrades();
                        }
                        case "学生选课添加" -> {

                        }
                        case "学生成绩添加/修改" -> {

                        }
                        case "学生选课/成绩删除" -> {

                        }
                    }
                }
            }
        });

        // listeners for the 2nd level tab panes
        teacherStudentInfoTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getText()) {
                case "查询" -> {
                    // load studentIDs
                    loadStudentIDComboBox(false);
                    initializeStudentQueryComboBoxes();
                }
                case "添加" -> {
                    // load the ComboBoxes
                    initializeStudentAddComboBoxes();
                }
            }
        });

        teacherAwardPunishmentTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getText()) {
                case "查询" -> {
                    // load award and punishment table views
                    loadAwards();
                    loadPunishments();
                }
                case "添加" -> {
                    initializeAwardLevelComboBoxes();
                }
                case "修改" -> {
                    initializeAwardLevelComboBoxes();
                }
                case "删除" -> {

                }
            }
        });

        teacherCourseTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getText()) {
                case "查询" -> {
                    loadCourses();
                }
                case "添加" -> {

                }
                case "修改" -> {

                }
                case "删除" -> {

                }
            }
        });

        teacherGradeTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getText()) {
                case "学生选课查询" -> {
                    loadStudentCurrentCourses();
                }
                case "学生成绩查询" -> {
                    loadStudentGradeStatistics();
                    loadStudentGrades();
                }
                case "学生选课添加" -> {

                }
                case "学生成绩添加/修改" -> {

                }
                case "学生选课/成绩删除" -> {

                }
            }
        });
    }


    @FXML
    private void handleLogoutButtonAction() {
        // reset the fields of this controller
        setTeacherUsername(null);
        setCurrentStudent(null);
        resetFlags();
        // switch to the login view
        SceneManager.getInstance().switchScene("login-view");
        // delete the student view
        SceneManager.getInstance().clearScene("teacher-view");
        // alert: "登出成功"
        showAlert("登出成功", "登出成功！");
    }

    @FXML
    private void handleQueryStudentButtonAction() {
        String studentID = studentIDComboBox.getValue();
        if (StudentDAO.isStringEmpty(studentID)) {
            showAlert("查询失败", "请先选择待查询学生学号！");
            return;
        }
        Student student = StudentDAO.queryStudent(studentID);
        if (student == null) {
            showAlert("查询失败", "未查询到该学号对应学生的信息！");
            return;
        }
        currentStudent = student;
        loadStudentInfo();
        // tables need reloading
        awardsLoaded = false;
        punishmentsLoaded = false;
        coursesLoaded = false;
        studentCoursesLoaded = false;
        studentGradeStatisticsLoaded = false;
        studentGradesLoaded = false;
    }

    @FXML
    private void handleRefreshButtonAction() {
        // reload studentIDs, clear other info
        loadStudentIDComboBox(true);
        resetFlags();
        clearStudentQueryInfo();    // note that currentStudent is set to null
        clearStudentAddInfo();
        clearAwardPunishmentAddInfo();
        clearAwardPunishmentUpdateInfo();
        clearAwardPunishmentDeleteInfo();
        clearCourseAddInfo();
        clearCourseUpdateInfo();
        clearCourseDeleteInfo();
        clearStudentCourseAddInfo();
        clearStudentGradeUpdateInfo();
        clearStudentGradeDeleteInfo();

        // reload the tables and statistics
        loadAwards();
        loadPunishments();
        loadCourses();
        loadStudentCurrentCourses();
        loadStudentGradeStatistics();
        loadStudentGrades();
    }

    @FXML
    private void handleSubmitStudentButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认添加该学生？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }

        String name = newNameTextField.getText();
        String ID = newIDTextField.getText();
        String gender = newGenderComboBox.getValue();
        LocalDate DOB = newDOBDatePicker.getValue();
        String ethnicity = newEthnicityComboBox.getValue();
        String politicalAffiliation = newPoliticalAffiliationComboBox.getValue();
        String studentID = newStudentIDTextField.getText();
        LocalDate enrolmentDate = newEnrolmentDatePicker.getValue();
        String major = newMajorComboBox.getValue();
        // if any of the fields is empty, return
        if (StudentDAO.isAnyStringEmpty(name, ID, gender, ethnicity, politicalAffiliation, studentID, major) || DOB == null) {
            showAlert("添加失败", "请填写所有信息！");
            return;
        }
        // create a new student
        Student student;
        if (enrolmentDate == null) {
            student = new Student(
                    ID, name, gender, Date.valueOf(DOB), ethnicity,
                    politicalAffiliation, studentID, null, major
            );
        } else {
            student = new Student(
                    ID, name, gender, Date.valueOf(DOB), ethnicity,
                    politicalAffiliation, studentID, Date.valueOf(enrolmentDate), major
            );
        }

        // insert the student into the database
        boolean success = StudentDAO.insertStudent(student);
        if (success) {
            showAlert("添加成功", "添加成功！");
            // the studentID ComboBox needs reloading
            studentIDComboBoxLoaded = false;
        } else {
            showAlert("添加失败", "添加失败！");
        }
    }

    @FXML
    private void handleDeleteStudentButtonAction() {
        // get the current selected studentID
        String studentID = studentIDComboBox.getValue();
        if (StudentDAO.isStringEmpty(studentID)) {
            showAlert("删除失败", "请选择一个学生！");
            return;
        }
        // show confirmation first
        String message = "是否确认删除学号为 " + studentID + "的学生？";
        Optional<ButtonType> confirmation = showConfirmation("确认", message);
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // call the procedure to delete the student
        boolean success = StudentDAO.deleteStudent(studentID);
        if (success) {
            showAlert("删除成功", "删除成功！");
            // force reload the studentID ComboBox
            loadStudentIDComboBox(true);
            // empty other info
            clearStudentQueryInfo();
            // tables need reloading
            awardsLoaded = false;
            punishmentsLoaded = false;
            coursesLoaded = false;
            studentCoursesLoaded = false;
            studentGradeStatisticsLoaded = false;
            studentGradesLoaded = false;
        } else {
            showAlert("删除失败", "删除失败！");
        }
    }

    @FXML public void handleAddAwardButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认添加该奖项？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String studentID = newAwardStudentIDTextField.getText();
        String awardName = newAwardNameTextField.getText();
        String awardLevel = newAwardLevelComboBox.getValue();
        LocalDate awardDate = newAwardDatePicker.getValue();
        if (StudentDAO.isAnyStringEmpty(studentID, awardName, awardLevel)) {
            showAlert("添加失败", "请填写所有信息！");
            return;
        }
        // create a new award
        Award award = new Award(studentID, awardName, awardLevel);
        if (awardDate != null) {
            award.setAwardDate(Date.valueOf(awardDate));
        }
        // insert the award into the database
        boolean success = AwardPunishmentDAO.insertAward(award);
        if (success) {
            showAlert("添加成功", "添加成功！");
            // the award table view needs reloading
            awardsLoaded = false;
        } else {
            showAlert("添加失败", "添加失败！");
        }
    }

    @FXML
    public void handleAddPunishmentButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认添加该惩罚？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String studentID = newPunishmentStudentIDTextField.getText();
        String punishmentName = newPunishmentNameTextField.getText();
        LocalDate punishmentDate = newPunishmentDatePicker.getValue();
        if (StudentDAO.isAnyStringEmpty(studentID, punishmentName)) {
            showAlert("添加失败", "请填写所有信息！");
            return;
        }
        // create a new punishment
        Punishment punishment = new Punishment(studentID, punishmentName);
        if (punishmentDate != null) {
            punishment.setPunishmentDate(Date.valueOf(punishmentDate));
        }
        // insert the punishment into the database
        boolean success = AwardPunishmentDAO.insertPunishment(punishment);
        if (success) {
            showAlert("添加成功", "添加成功！");
            // the punishment table view needs reloading
            punishmentsLoaded = false;
        } else {
            showAlert("添加失败", "添加失败！");
        }
    }

    @FXML
    public void handleUpdateAwardButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认更新该奖项？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String studentID = updateAwardStudentIDTextField.getText();
        String oldAwardName = updateAwardOldNameTextField.getText();
        String newAwardName = updateAwardNewNameTextField.getText();
        String newAwardLevel = updateAwardNewLevelComboBox.getValue();
        LocalDate newAwardDate = updateAwardNewDatePicker.getValue();
        if (StudentDAO.isStringEmpty(studentID)) {
            showAlert("更新失败", "请填写学生学号！");
            return;
        }
        if (StudentDAO.isStringEmpty(oldAwardName)) {
            showAlert("更新失败", "请填写原奖项名称！");
            return;
        }
        // at least one field should be updated
        if (StudentDAO.areAllStringsEmpty(newAwardName, newAwardLevel) && newAwardDate == null) {
            showAlert("更新失败", "请填写至少一个新的奖项信息！");
            return;
        }
        // update the award
        boolean success;
        if (newAwardDate == null) {
            success = AwardPunishmentDAO.updateAward(studentID, oldAwardName, newAwardName, newAwardLevel, null);
        } else {
            success = AwardPunishmentDAO.updateAward(studentID, oldAwardName, newAwardName, newAwardLevel, Date.valueOf(newAwardDate));
        }
        if (success) {
            showAlert("更新成功", "更新成功！");
            // the award table view needs reloading
            awardsLoaded = false;
        } else {
            showAlert("更新失败", "更新失败！");
        }
    }

    @FXML
    private void handleUpdatePunishmentButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认更新该惩罚？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String studentID = updatePunishmentStudentIDTextField.getText();
        String oldPunishmentName = updatePunishmentOldNameTextField.getText();
        String newPunishmentName = updatePunishmentNewNameTextField.getText();
        LocalDate newPunishmentDate = updatePunishmentNewDatePicker.getValue();
        if (StudentDAO.isStringEmpty(studentID)) {
            showAlert("更新失败", "请填写学生学号！");
            return;
        }
        if (StudentDAO.isStringEmpty(oldPunishmentName)) {
            showAlert("更新失败", "请填写原惩罚名称！");
            return;
        }
        // at least one field should be updated
        if (StudentDAO.isStringEmpty(newPunishmentName) && newPunishmentDate == null) {
            showAlert("更新失败", "请填写至少一个新的惩罚信息！");
            return;
        }
        // update the punishment
        boolean success;
        if (newPunishmentDate == null) {
            success = AwardPunishmentDAO.updatePunishment(studentID, oldPunishmentName, newPunishmentName, null);
        } else {
            success = AwardPunishmentDAO.updatePunishment(studentID, oldPunishmentName, newPunishmentName, Date.valueOf(newPunishmentDate));
        }
        if (success) {
            showAlert("更新成功", "更新成功！");
            // the punishment table view needs reloading
            punishmentsLoaded = false;
        } else {
            showAlert("更新失败", "更新失败！");
        }
    }

    @FXML
    public void handleDeleteAwardButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认删除该奖项？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String studentID = deleteAwardStudentIDTextField.getText();
        String awardName = deleteAwardNameTextField.getText();
        if (StudentDAO.isAnyStringEmpty(studentID, awardName)) {
            showAlert("删除失败", "请填写所有信息！");
            return;
        }
        // delete the award
        boolean success = AwardPunishmentDAO.deleteAward(studentID, awardName);
        if (success) {
            showAlert("删除成功", "删除成功！");
            // the award table view needs reloading
            awardsLoaded = false;
        } else {
            showAlert("删除失败", "删除失败！");
        }
    }

    @FXML
    public void handleDeletePunishmentButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认删除该惩罚？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String studentID = deletePunishmentStudentIDTextField.getText();
        String punishmentName = deletePunishmentNameTextField.getText();
        if (StudentDAO.isAnyStringEmpty(studentID, punishmentName)) {
            showAlert("删除失败", "请填写所有信息！");
            return;
        }
        // delete the punishment
        boolean success = AwardPunishmentDAO.deletePunishment(studentID, punishmentName);
        if (success) {
            showAlert("删除成功", "删除成功！");
            // the punishment table view needs reloading
            punishmentsLoaded = false;
        } else {
            showAlert("删除失败", "删除失败！");
        }
    }

    @FXML
    public void handleAddNewCourseButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认添加该课程？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String courseID = newCourseIDTextField.getText();
        String courseName = newCourseNameTextField.getText();
        String credits = newCourseCreditsTextField.getText();
        if (StudentDAO.isAnyStringEmpty(courseID, courseName, credits)) {
            showAlert("添加失败", "请填写所有信息！");
            return;
        }
        // create a new course
        Course course = new Course(courseName, courseID, credits);
        // insert the course into the database
        boolean success = CourseDAO.insertCourse(course);
        if (success) {
            showAlert("添加成功", "添加成功！");
            // the course table view needs reloading
            coursesLoaded = false;
        } else {
            showAlert("添加失败", "添加失败！");
        }
    }

    @FXML
    public void handleUpdateCourseButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认更新该课程？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String oldCourseID = updateOldCourseIDTextField.getText();
        String newCourseID = updateNewCourseIDTextField.getText();
        String newCourseName = updateNewCourseNameTextField.getText();
        String newCredits = updateNewCourseCreditsTextField.getText();
        if (StudentDAO.isStringEmpty(oldCourseID)) {
            showAlert("更新失败", "请填写原课程号！");
            return;
        }
        // at least one field should be updated
        if (StudentDAO.areAllStringsEmpty(newCourseID, newCourseName, newCredits)) {
            showAlert("更新失败", "请填写至少一个新的课程信息！");
            return;
        }
        // update the course
        boolean success = CourseDAO.updateCourse(oldCourseID, newCourseID, newCourseName, newCredits);
        if (success) {
            showAlert("更新成功", "更新成功！");
            // the course table view needs reloading
            coursesLoaded = false;
            studentCoursesLoaded = false;
            studentGradesLoaded = false;
        } else {
            showAlert("更新失败", "更新失败！");
        }
    }

    @FXML
    public void handleDeleteCourseButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认删除该课程？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if the courseID is empty, return
        String courseID = deleteCourseIDTextField.getText();
        if (StudentDAO.isStringEmpty(courseID)) {
            showAlert("删除失败", "请填写课程号！");
            return;
        }
        // delete the course
        boolean success = CourseDAO.deleteCourse(courseID);
        if (success) {
            showAlert("删除成功", "删除成功！");
            // the course table view needs reloading
            coursesLoaded = false;
            studentCoursesLoaded = false;
            studentGradesLoaded = false;
        } else {
            showAlert("删除失败", "删除失败！");
        }
    }

    @FXML
    public void handleAddStudentCourseButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认添加该选课？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String studentID = addCourseStudentIDTextField.getText();
        String courseID = addCourseCourseIDTextField.getText();
        if (StudentDAO.isAnyStringEmpty(studentID, courseID)) {
            showAlert("添加失败", "请填写所有信息！");
            return;
        }
        // add the course for the student
        boolean success = GradeDAO.addCourseForStudent(studentID, courseID);
        if (success) {
            showAlert("添加成功", "添加成功！");
            // the student course table view needs reloading
            studentCoursesLoaded = false;
        } else {
            showAlert("添加失败", "添加失败！");
        }
    }

    @FXML
    public void handleUpdateStudentGradeButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认更新该成绩？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String studentID = updateCourseGradeStudentIDTextField.getText();
        String courseID = updateCourseGradeCourseIDTextField.getText();
        String newGrade = updateCourseGradeNewGradeTextField.getText();
        if (StudentDAO.isAnyStringEmpty(studentID, courseID, newGrade)) {
            showAlert("更新失败", "请填写所有信息！");
            return;
        }
        // update the grade
        try {
            boolean success = GradeDAO.updateGrade(studentID, courseID, Integer.parseInt(newGrade));
            if (success) {
                showAlert("更新成功", "更新成功！");
                // the student grade table view needs reloading
                studentGradesLoaded = false;
                studentCoursesLoaded = false;
                studentGradeStatisticsLoaded = false;
            } else {
                showAlert("更新失败", "更新失败！");
            }
        } catch (NumberFormatException e) {
            showAlert("更新失败", "成绩应为整数！");
        }
    }

    @FXML
    public void handleDeleteStudentGradeButtonAction() {
        // show confirmation first
        Optional<ButtonType> confirmation = showConfirmation("确认", "是否确认删除该选课？");
        if (confirmation.isPresent()) {
            switch (confirmation.get().getText()) {
                case "是" -> {
                    // proceed
                }
                case "否" -> {
                    return;
                }
                default -> {
                    // the user closed the dialog
                    return;
                }
            }
        }
        // if any of the required fields is empty, return
        String studentID = deleteCourseStudentIDTextField.getText();
        String courseID = deleteCourseCourseIDTextField.getText();
        if (StudentDAO.isAnyStringEmpty(studentID, courseID)) {
            showAlert("删除失败", "请填写所有信息！");
            return;
        }
        // delete the course for the student
        boolean success = GradeDAO.deleteGrade(studentID, courseID);
        if (success) {
            showAlert("删除成功", "删除成功！");
            // the student course table view needs reloading
            studentCoursesLoaded = false;
            studentGradesLoaded = false;
            studentGradeStatisticsLoaded = false;
        } else {
            showAlert("删除失败", "删除失败！");
        }
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public void initializeStudentQueryComboBoxes() {
        if (studentQueryComboBoxesLoaded) {
            return;
        }
        setComboBox(genderComboBox, GENDERS);
        setComboBox(ethnicityComboBox, ETHNICITIES);
        setComboBox(politicalAffiliationComboBox, POLITICAL_AFFILIATIONS);
        setComboBox(majorComboBox, MAJORS);
        studentQueryComboBoxesLoaded = true;
    }

    public void initializeStudentAddComboBoxes() {
        if (studentAddComboBoxesLoaded) {
            return;
        }
        setComboBox(newGenderComboBox, GENDERS);
        setComboBox(newEthnicityComboBox, ETHNICITIES);
        setComboBox(newPoliticalAffiliationComboBox, POLITICAL_AFFILIATIONS);
        setComboBox(newMajorComboBox, MAJORS);
        studentAddComboBoxesLoaded = true;
    }

    public void initializeAwardLevelComboBoxes() {
        if (awardLevelComboBoxesLoaded) {
            return;
        }
        setComboBox(newAwardLevelComboBox, AWARD_LEVELS);
        setComboBox(updateAwardNewLevelComboBox, AWARD_LEVELS);
        awardLevelComboBoxesLoaded = true;
    }

    public void loadWelcomeLabel() {
        if (teacherUsername == null || welcomeLabelLoaded) {
            return;
        }
        setText(welcomeLabel, "欢迎，" + teacherUsername + "!");
        welcomeLabelLoaded = true;
    }

    public void loadStudentIDComboBox() {
        if (studentIDComboBoxLoaded)
            return;
        List<String> studentIDs = StudentDAO.queryStudentIDsASEC();
        studentIDComboBox.setItems(FXCollections.observableArrayList(studentIDs));
        studentIDComboBoxLoaded = true;
    }

    public void loadStudentIDComboBox(boolean force) {
        if (force) {
            studentIDComboBoxLoaded = false;
        }
        loadStudentIDComboBox();
    }

    public void loadImageView(String photoURL) {
        photoImageView.setImage(null);
        if (photoURL == null) {
//            photoImageView.setImage(null);
            return;
        }
        setImage(photoImageView, photoURL);
    }

    public void loadStudentInfo() {
        Student student = currentStudent;
        if (student == null) {
            return;
        }
        loadImageView(student.getPhotoURL());
        setText(nameTextField, student.getName());
        setText(IDTextField, student.getID());
        setText(genderComboBox, student.getGender());
        setText(DOBTextField, student.getDOB().toString());
        setText(ethnicityComboBox, student.getEthnicity());
        setText(politicalAffiliationComboBox, student.getPoliticalAffiliation());
        setText(phoneNumberTextField, student.getPhoneNumber());
        setText(emailTextField, student.getEmail());
        setText(studentIDTextField, student.getStudentID());
        setText(enrolmentDateTextField, student.getEnrolmentDate().toString());
        setText(majorComboBox, student.getMajor());
    }

    public void loadAwards() {
        if (awardsLoaded) {
            return;
        }
        if (currentStudent == null) {
            awardTableView.setItems(FXCollections.observableArrayList());
            return;
        }
        awardTableView.setItems(FXCollections.observableArrayList(AwardPunishmentDAO.queryAwards(currentStudent.getStudentID())));
        awardsLoaded = true;
    }

    public void loadAwards(boolean force) {
        if (force) {
            awardsLoaded = false;
        }
        loadAwards();
    }

    public void loadPunishments() {
        if (punishmentsLoaded) {
            return;
        }
        if (currentStudent == null) {
            punishmentTableView.setItems(FXCollections.observableArrayList());
            return;
        }
        punishmentTableView.setItems(FXCollections.observableArrayList(AwardPunishmentDAO.queryPunishments(currentStudent.getStudentID())));
        punishmentsLoaded = true;
    }

    public void loadPunishments(boolean force) {
        if (force) {
            punishmentsLoaded = false;
        }
        loadPunishments();
    }

    public void loadCourses() {
        if (coursesLoaded) {
            return;
        }
        courseTableView.setItems(FXCollections.observableArrayList(CourseDAO.queryCourses()));
        coursesLoaded = true;
    }

    public void loadCourses(boolean force) {
        if (force) {
            coursesLoaded = false;
        }
        loadCourses();
    }

    public void loadStudentCurrentCourses() {
        if (studentCoursesLoaded) {
            return;
        }
        if (currentStudent == null) {
            studentCourseTableView.setItems(FXCollections.observableArrayList());
            return;
        }
        studentCourseTableView.setItems(FXCollections.observableArrayList(GradeDAO.queryStudentCurrentCourses(currentStudent.getStudentID())));
    }

    public void loadStudentCurrentCourses(boolean force) {
        if (force) {
            studentCoursesLoaded = false;
        }
        loadStudentCurrentCourses();
    }

    public void loadStudentGrades() {
        if (studentGradesLoaded) {
            return;
        }
        if (currentStudent == null) {
            studentGradeTableView.setItems(FXCollections.observableArrayList());
            return;
        }
        studentGradeTableView.setItems(FXCollections.observableArrayList(GradeDAO.queryStudentGrades(currentStudent.getStudentID())));
    }

    public void loadStudentGrades(boolean force) {
        if (force) {
            studentGradesLoaded = false;
        }
        loadStudentGrades();
    }

    public boolean loadPassedCredits() {
        if (currentStudent == null) {
            clear(studentGradePassedCreditsTextField);
            return false;
        }
        String passedCredits = GradeDAO.getPassedCredits(currentStudent.getStudentID());
        if (passedCredits == null) {
            clear(studentGradePassedCreditsTextField);
            return false;
        }
        setText(studentGradePassedCreditsTextField, passedCredits);
        return true;
    }

    public boolean loadFailedCourseNumber() {
        if (currentStudent == null) {
            clear(studentGradeFailedCourseNumberTextField);
            return false;
        }
        Integer failedCourseNumber = GradeDAO.getFailedCourseNum(currentStudent.getStudentID());
        if (failedCourseNumber == null) {
            clear(studentGradeFailedCourseNumberTextField);
            return false;
        }
        setText(studentGradeFailedCourseNumberTextField, failedCourseNumber.toString());
        return true;
    }

    public boolean loadFailedCredits() {
        if (currentStudent == null) {
            clear(studentGradeFailedCreditsTextField);
            return false;
        }
        String failedCredits = GradeDAO.getFailedCredits(currentStudent.getStudentID());
        if (failedCredits == null) {
            clear(studentGradeFailedCreditsTextField);
            return false;
        }
        setText(studentGradeFailedCreditsTextField, failedCredits);
        return true;
    }

    public boolean loadWeightedAverageScore() {
        if (currentStudent == null) {
            clear(studentGradeWeightedAverageScoreTextField);
            return false;
        }
        // weighted average score can be null when there are no scores
        Float weightedAverageScore = GradeDAO.getWeightedAverageScore(currentStudent.getStudentID());
        if (weightedAverageScore == null) {
            clear(studentGradeWeightedAverageScoreTextField);
            // but it can also be because of an error, so we return false regardless of the reason
            return false;
        }
        setText(studentGradeWeightedAverageScoreTextField, String.format("%.2f", weightedAverageScore));
        return true;
    }

    public void loadStudentGradeStatistics() {
        if (studentGradeStatisticsLoaded) {
            return;
        }
//        studentGradeStatasticsLoaded = loadPassedCredits() && loadFailedCourseNumber() && loadFailedCredits() && loadWeightedAverageScore();
        boolean passedCreditsLoaded = loadPassedCredits();
        boolean failedCourseNumberLoaded = loadFailedCourseNumber();
        boolean failedCreditsLoaded = loadFailedCredits();
        boolean weightedAverageScoreLoaded = loadWeightedAverageScore();
        studentGradeStatisticsLoaded = passedCreditsLoaded && failedCourseNumberLoaded && failedCreditsLoaded && weightedAverageScoreLoaded;
    }

    public void loadStudentGradeStatistics(boolean force) {
        if (force) {
            studentGradeStatisticsLoaded = false;
        }
        loadStudentGradeStatistics();
    }

    public void clearStudentQueryInfo() {
        currentStudent = null;
        studentIDComboBox.setValue(null);
        clear(photoImageView);
        clear(nameTextField);
        clear(IDTextField);
        clear(genderComboBox);
        clear(DOBTextField);
        clear(ethnicityComboBox);
        clear(politicalAffiliationComboBox);
        clear(phoneNumberTextField);
        clear(emailTextField);
        clear(studentIDTextField);
        clear(enrolmentDateTextField);
        clear(majorComboBox);
    }

    public void clearStudentAddInfo() {
        clear(newNameTextField);
        clear(newIDTextField);
        clear(newGenderComboBox);
        clear(newDOBDatePicker);
        clear(newEthnicityComboBox);
        clear(newPoliticalAffiliationComboBox);
        clear(newStudentIDTextField);
        clear(newEnrolmentDatePicker);
        clear(newMajorComboBox);
    }

    public void clearAwardPunishmentAddInfo() {
        clear(newAwardStudentIDTextField);
        clear(newAwardNameTextField);
        clear(newAwardLevelComboBox);
        clear(newAwardDatePicker);

        clear(newPunishmentStudentIDTextField);
        clear(newPunishmentNameTextField);
        clear(newPunishmentDatePicker);
    }

    public void clearAwardPunishmentUpdateInfo() {
        clear(updateAwardStudentIDTextField);
        clear(updateAwardOldNameTextField);
        clear(updateAwardNewNameTextField);
        clear(updateAwardNewLevelComboBox);
        clear(updateAwardNewDatePicker);

        clear(updatePunishmentStudentIDTextField);
        clear(updatePunishmentOldNameTextField);
        clear(updatePunishmentNewNameTextField);
        clear(updatePunishmentNewDatePicker);
    }

    public void clearAwardPunishmentDeleteInfo() {
        clear(deleteAwardStudentIDTextField);
        clear(deleteAwardNameTextField);

        clear(deletePunishmentStudentIDTextField);
        clear(deletePunishmentNameTextField);
    }

    public void clearCourseAddInfo() {
        clear(newCourseIDTextField);
        clear(newCourseNameTextField);
        clear(newCourseCreditsTextField);
    }

    public void clearCourseUpdateInfo() {
        clear(updateOldCourseIDTextField);
        clear(updateNewCourseIDTextField);
        clear(updateNewCourseNameTextField);
        clear(updateNewCourseCreditsTextField);
    }

    public void clearCourseDeleteInfo() {
        clear(deleteCourseIDTextField);
    }

    public void clearStudentCourseAddInfo() {
        clear(addCourseStudentIDTextField);
        clear(addCourseCourseIDTextField);
    }

    public void clearStudentGradeUpdateInfo() {
        clear(updateCourseGradeStudentIDTextField);
        clear(updateCourseGradeCourseIDTextField);
        clear(updateCourseGradeNewGradeTextField);
    }

    public void clearStudentGradeDeleteInfo() {
        clear(deleteCourseStudentIDTextField);
        clear(deleteCourseCourseIDTextField);
    }

    public void resetFlags() {
        welcomeLabelLoaded = false;
        studentQueryComboBoxesLoaded = false;
        studentAddComboBoxesLoaded = false;
        studentIDComboBoxLoaded = false;
        awardsLoaded = false;
        punishmentsLoaded = false;
        awardLevelComboBoxesLoaded = false;
        coursesLoaded = false;
        studentCoursesLoaded = false;
        studentGradesLoaded = false;
        studentGradeStatisticsLoaded = false;
    }

    public void updateStudentName() {
        if (currentStudent == null) {
            return;
        }
        String oldName = currentStudent.getName();
        String newName = nameTextField.getText();
        if (StudentDAO.isStringEmpty(newName) || newName.equals(oldName)) {
            return;
        }
        String ID = currentStudent.getID();
        boolean success = StudentDAO.updateStudentName(newName, ID);
        currentStudent.setName(newName);
        if (success) {
            showAlert("更新成功", "姓名更新成功！");
        } else {
            // rollback
            currentStudent.setName(oldName);
            nameTextField.setText(oldName);
            showAlert("更新失败", "姓名更新失败！");
        }
    }

    public void updateStudentGender() {
        if (currentStudent == null) {
            return;
        }
        String oldGender = currentStudent.getGender();
        String newGender = genderComboBox.getValue();
        if (StudentDAO.isStringEmpty(newGender) || newGender.equals(oldGender)) {
            return;
        }
        String ID = currentStudent.getID();
        boolean success = StudentDAO.updateStudentGender(newGender, ID);
        currentStudent.setGender(newGender);
        if (success) {
            showAlert("更新成功", "性别更新成功！");
        } else {
            // rollback
            currentStudent.setGender(oldGender);
            genderComboBox.setValue(oldGender);
            showAlert("更新失败", "性别更新失败！");
        }
    }

    public void updateStudentEthnicity() {
        if (currentStudent == null) {
            return;
        }
        String oldEthnicity = currentStudent.getEthnicity();
        String newEthnicity = ethnicityComboBox.getValue();
        if (StudentDAO.isStringEmpty(newEthnicity) || newEthnicity.equals(oldEthnicity)) {
            return;
        }
        String ID = currentStudent.getID();
        boolean success = StudentDAO.updateStudentEthnicity(newEthnicity, ID);
        currentStudent.setEthnicity(newEthnicity);
        if (success) {
            showAlert("更新成功", "民族更新成功！");
        } else {
            // rollback
            currentStudent.setEthnicity(oldEthnicity);
            ethnicityComboBox.setValue(oldEthnicity);
            showAlert("更新失败", "民族更新失败！");
        }
    }

    public void updateStudentPoliticalAffiliation() {
        if (currentStudent == null) {
            return;
        }
        String oldPoliticalAffiliation = currentStudent.getPoliticalAffiliation();
        String newPoliticalAffiliation = politicalAffiliationComboBox.getValue();
        if (StudentDAO.isStringEmpty(newPoliticalAffiliation) || newPoliticalAffiliation.equals(oldPoliticalAffiliation)) {
            return;
        }
        String ID = currentStudent.getID();
        boolean success = StudentDAO.updateStudentPoliticalAffiliation(newPoliticalAffiliation, ID);
        currentStudent.setPoliticalAffiliation(newPoliticalAffiliation);
        if (success) {
            showAlert("更新成功", "政治面貌更新成功！");
        } else {
            // rollback
            currentStudent.setPoliticalAffiliation(oldPoliticalAffiliation);
            politicalAffiliationComboBox.setValue(oldPoliticalAffiliation);
            showAlert("更新失败", "政治面貌更新失败！");
        }
    }

    public void updateStudentID() {
        if (currentStudent == null) {
            return;
        }
        String oldStudentID = currentStudent.getStudentID();
        String newStudentID = studentIDTextField.getText();
        if (StudentDAO.isStringEmpty(newStudentID) || newStudentID.equals(oldStudentID)) {
            return;
        }
        boolean success = StudentDAO.updateStudentID(newStudentID, oldStudentID);
        currentStudent.setStudentID(newStudentID);
        if (success) {
            // refresh
            loadStudentIDComboBox(true);
            clearStudentQueryInfo();
            showAlert("更新成功", "学号更新成功！");
        } else {
            // rollback
            currentStudent.setStudentID(oldStudentID);
            studentIDTextField.setText(oldStudentID);
            showAlert("更新失败", "学号更新失败！");
        }
    }

    public void updateStudentMajor() {
        if (currentStudent == null) {
            return;
        }
        String oldMajor = currentStudent.getMajor();
        String newMajor = majorComboBox.getValue();
        if (StudentDAO.isStringEmpty(newMajor) || newMajor.equals(oldMajor)) {
            return;
        }
        String studentID = currentStudent.getStudentID();
        boolean success = StudentDAO.updateStudentMajor(newMajor, studentID);
        currentStudent.setMajor(newMajor);
        if (success) {
            showAlert("更新成功", "专业更新成功！");
        } else {
            // rollback
            currentStudent.setMajor(oldMajor);
            majorComboBox.setValue(oldMajor);
            showAlert("更新失败", "专业更新失败！");
        }
    }
}
