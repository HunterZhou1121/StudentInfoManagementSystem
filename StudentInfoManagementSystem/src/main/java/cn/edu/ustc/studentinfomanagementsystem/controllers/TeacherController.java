package cn.edu.ustc.studentinfomanagementsystem.controllers;

import cn.edu.ustc.studentinfomanagementsystem.DAO.AwardPunishmentDAO;
import cn.edu.ustc.studentinfomanagementsystem.DAO.CourseDAO;
import cn.edu.ustc.studentinfomanagementsystem.DAO.StudentDAO;
import cn.edu.ustc.studentinfomanagementsystem.SceneManager;
import cn.edu.ustc.studentinfomanagementsystem.models.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    private String teacherUsername;

    private StudentDAO studentDAO = new StudentDAO();

    private AwardPunishmentDAO awardPunishmentDAO = new AwardPunishmentDAO();

    private CourseDAO courseDAO = new CourseDAO();

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

    @FXML private DatePicker enrolmentDatePicker;

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



    private boolean welcomeLabelLoaded = false;

    private boolean studentQueryComboBoxesLoaded = false;

    private boolean studentAddComboBoxesLoaded = false;

    private boolean studentIDComboBoxLoaded = false;

    @FXML
    private void initialize() {
        // set the items of the ComboBoxes
        // gender
//        genderComboBox.setItems(FXCollections.observableArrayList("男", "女"));
//        genderComboBox.setItems(FXCollections.observableArrayList(GENDERS));
        // ethnicity
//        ethnicityComboBox.setItems(FXCollections.observableArrayList(
//            "汉族",
//            "蒙古族",
//            "回族",
//            "藏族",
//            "维吾尔族",
//            "苗族",
//            "彝族",
//            "壮族",
//            "布依族",
//            "朝鲜族",
//            "满族",
//            "侗族",
//            "瑶族",
//            "白族",
//            "土家族",
//            "哈尼族",
//            "哈萨克族",
//            "傣族",
//            "黎族",
//            "傈僳族",
//            "佤族",
//            "畲族",
//            "高山族",
//            "拉祜族",
//            "水族",
//            "东乡族",
//            "纳西族",
//            "景颇族",
//            "柯尔克孜族",
//            "土族",
//            "达斡尔族",
//            "仫佬族",
//            "羌族",
//            "布朗族",
//            "撒拉族",
//            "毛南族",
//            "仡佬族",
//            "锡伯族",
//            "阿昌族",
//            "普米族",
//            "塔吉克族",
//            "怒族",
//            "乌孜别克族",
//            "俄罗斯族",
//            "鄂温克族",
//            "德昂族",
//            "保安族",
//            "裕固族",
//            "京族",
//            "塔塔尔族",
//            "独龙族",
//            "鄂伦春族",
//            "赫哲族",
//            "门巴族",
//            "珞巴族",
//            "基诺族"
//        ));
//        ethnicityComboBox.setItems(FXCollections.observableArrayList(ETHNICITIES));
        // political affiliation
//        politicalAffiliationComboBox.setItems(FXCollections.observableArrayList(
//            "中共党员",
//            "中共预备党员",
//            "共青团员",
//            "民革会员",
//            "民盟盟员",
//            "民建会员",
//            "民进会员",
//            "农工党党员",
//            "致公党党员",
//            "九三学社社员",
//            "台盟盟员",
//            "无党派民主人士",
//            "群众"
//        ));
//        politicalAffiliationComboBox.setItems(FXCollections.observableArrayList(POLITICAL_AFFILIATIONS));
        // major
//        majorComboBox.setItems(FXCollections.observableArrayList(
//            "数学与应用数学",
//            "信息与计算科学",
//            "物理学",
//            "应用物理学",
//            "天文学",
//            "光电信息科学与工程",
//            "工商管理",
//            "信息管理与信息系统",
//            "管理科学",
//            "金融学",
//            "统计学",
//            "化学",
//            "材料物理",
//            "材料化学",
//            "高分子材料与工程",
//            "地球物理学",
//            "大气科学",
//            "空间科学与技术",
//            "地球化学",
//            "行星科学",
//            "环境科学",
//            "理论与应用力学",
//            "机械设计制造及其自动化",
//            "测控技术与仪器",
//            "能源与动力工程",
//            "安全工程",
//            "英语",
//            "考古学",
//            "传播学",
//            "网络与新媒体",
//            "核工程与核技术",
//            "工程物理",
//            "应用物理学",
//            "环境科学与工程",
//            "量子信息科学",
//            "生物科学",
//            "生物技术",
//            "临床医学",
//            "电子信息工程",
//            "通信工程",
//            "自动化",
//            "人工智能",
//            "计算机科学与技术",
//            "软件工程",
//            "电子科学与技术",
//            "信息安全",
//            "网络空间安全",
//            "数据科学与大数据技术"
//        ));
//        majorComboBox.setItems(FXCollections.observableArrayList(MAJORS));
        initializeStudentQueryComboBoxes();
        // listeners for the 1st level tab pane
        teacherTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getText()) {
                case "学生信息" -> {
                    // consider 2nd level tab pane
                    String subTabName = teacherStudentInfoTabPane.getSelectionModel().selectedItemProperty().getName();
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
    }

    @FXML
    private void handleLogoutButtonAction() {
        // reset the student
        setTeacherUsername(null);
        // switch to the login view
        SceneManager.getInstance().switchScene("login-view");
        // delete the student view
        SceneManager.getInstance().clearScene("teacher-view");
        // alert: "登出成功"
        showAlert("登出成功", "登出成功！");
    }

    @FXML
    private void handleQueryButtonAction() {
        String studentID = studentIDComboBox.getValue();
        if (studentID == null) {
            showAlert("查询失败", "请先选择待查询学生学号！");
            return;
        }
        Student student = studentDAO.queryStudent(studentID);
        if (student == null) {
            showAlert("查询失败", "未查询到该学号对应学生的信息！");
            return;
        }
        currentStudent = student;
        loadStudentInfo();
    }

    @FXML
    private void handleRefreshButtonAction() {
        // reload studentIDs, clear other info
        loadStudentIDComboBox(true);
        clearStudentQueryInfo();
        clearStudentAddInfo();
    }

    @FXML
    private void handleSubmitButtonAction() {
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
        if (name == null || ID == null || gender == null || DOB == null ||
                ethnicity == null || politicalAffiliation == null || studentID == null ||
                enrolmentDate == null || major == null) {
            showAlert("添加失败", "请填写所有信息！");
        }
        // create a new student
        Student student = new Student(
                ID, name, gender, Date.valueOf(DOB), ethnicity,
                politicalAffiliation, studentID, Date.valueOf(enrolmentDate), major
        );
        // insert the student into the database
        boolean success = studentDAO.insertStudent(student);
        if (success) {
            showAlert("添加成功", "添加成功！");
            // the studentID ComboBox needs reloading
            studentIDComboBoxLoaded = false;
        } else {
            showAlert("添加失败", "添加失败！");
        }
    }

    @FXML
    private void handleDeleteButtonAction() {
        // get the current selected studentID
        String studentID = studentIDComboBox.getValue();
        if (studentID == null) {
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
        boolean success = studentDAO.deleteStudent(studentID);
        if (success) {
            showAlert("删除成功", "删除成功！");
            // force reload the studentID ComboBox
            loadStudentIDComboBox(true);
            // empty other info
            clearStudentQueryInfo();
        } else {
            showAlert("删除失败", "删除失败！");
        }
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
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
        List<String> studentIDs = studentDAO.queryStudentIDsASEC();
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
        if (photoURL == null) {
            photoImageView.setImage(null);
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
//        setDate(enrolmentDatePicker, student.getEnrolmentDate());
        setText(enrolmentDateTextField, student.getEnrolmentDate().toString());
        setText(majorComboBox, student.getMajor());
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
//        clear(enrolmentDatePicker);
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

    public void updateStudentName() {
        if (currentStudent == null) {
            return;
        }
        String oldName = currentStudent.getName();
        String newName = nameTextField.getText();
        if (newName == null || newName.equals(oldName)) {
            return;
        }
        String ID = currentStudent.getID();
        boolean success = studentDAO.updateStudentName(newName, ID);
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
        if (newGender == null || newGender.equals(oldGender)) {
            return;
        }
        String ID = currentStudent.getID();
        boolean success = studentDAO.updateStudentGender(newGender, ID);
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
        if (newEthnicity == null || newEthnicity.equals(oldEthnicity)) {
            return;
        }
        String ID = currentStudent.getID();
        boolean success = studentDAO.updateStudentEthnicity(newEthnicity, ID);
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
        if (newPoliticalAffiliation == null || newPoliticalAffiliation.equals(oldPoliticalAffiliation)) {
            return;
        }
        String ID = currentStudent.getID();
        boolean success = studentDAO.updateStudentPoliticalAffiliation(newPoliticalAffiliation, ID);
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
        if (newStudentID == null || newStudentID.equals(oldStudentID)) {
            return;
        }
        boolean success = studentDAO.updateStudentID(newStudentID, oldStudentID);
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

//    public void updateStudentEnrolmentDate() {
//        if (currentStudent == null) {
//            return;
//        }
//        Date oldEnrolmentDate = currentStudent.getEnrolmentDate();
//        LocalDate newEnrolmentDate = enrolmentDatePicker.getValue();
//        if (newEnrolmentDate == null || newEnrolmentDate.equals(oldEnrolmentDate.toLocalDate())) {
//            return;
//        }
//        String studentID = currentStudent.getStudentID();
//        boolean success = studentDAO.updateStudentEnrolmentDate(Date.valueOf(newEnrolmentDate), studentID);
//        currentStudent.setEnrolmentDate(Date.valueOf(newEnrolmentDate));
//        if (success) {
//            showAlert("更新成功", "入学日期更新成功！");
//        } else {
//            // rollback
//            currentStudent.setEnrolmentDate(oldEnrolmentDate);
//            enrolmentDatePicker.setValue(oldEnrolmentDate.toLocalDate());
//            showAlert("更新失败", "入学日期更新失败！");
//        }
//    }

    public void updateStudentMajor() {
        if (currentStudent == null) {
            return;
        }
        String oldMajor = currentStudent.getMajor();
        String newMajor = majorComboBox.getValue();
        if (newMajor == null || newMajor.equals(oldMajor)) {
            return;
        }
        String studentID = currentStudent.getStudentID();
        boolean success = studentDAO.updateStudentMajor(newMajor, studentID);
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
