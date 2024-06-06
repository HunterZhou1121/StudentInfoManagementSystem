# DROP DATABASE IF EXISTS lab02;
# CREATE DATABASE lab02;
# USE lab02;
DROP TABLE IF EXISTS Grade;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Award;
DROP TABLE IF EXISTS Punishment;
DROP TABLE IF EXISTS StudentAccount;
DROP TABLE IF EXISTS TeacherAccount;
DROP TABLE IF EXISTS Enrolment;
DROP TABLE IF EXISTS Student;

CREATE TABLE Student(
    ID VARCHAR(18) PRIMARY KEY,                     -- Identification number, 18 digits
    PhotoURL VARCHAR(255) UNIQUE,                   -- URL of the photo
    Name VARCHAR(50) NOT NULL,                      -- Name of the student
    Gender VARCHAR(10) NOT NULL,                    -- Gender of the student, either '男' or '女'. Primitive.
    DOB DATE NOT NULL,                              -- Date of birth, no later than the current date
    Ethnicity VARCHAR(50) NOT NULL,                 -- Ethnicity such as '汉'
    PoliticalAffiliation VARCHAR(50) NOT NULL,      -- Political affiliation, from a given set which will be defined below
    PhoneNumber VARCHAR(20),                        -- Phone number. In China, they have 11 digits.
    Email VARCHAR(255) UNIQUE,                      -- Email address
    -- Define the constraints here
    -- Gender: '男' or '女'
    CHECK (Gender IN ('男', '女')),
    /*
        Political affiliation:
            '中共党员', '中共预备党员', '共青团员', '民革会员', '民盟盟员',
            '民建会员', '民进会员', '农工党党员'， '致公党党员', '九三学社社员',
            '台盟盟员', '无党派民主人士', '群众'
    */
    CHECK (
        PoliticalAffiliation IN (
        '中共党员', '中共预备党员', '共青团员', '民革会员', '民盟盟员',
        '民建会员', '民进会员', '农工党党员', '致公党党员', '九三学社社员',
        '台盟盟员', '无党派民主人士', '群众'
        )
    ),
    -- Phone number: 11 digits that start with 1
    CHECK (
      PhoneNumber REGEXP '^1[0-9]{10}$'
    ),
    -- Email address: A valid email address
    /*
        ^: Beginning of the string
        [a-zA-Z0-9._%+-]+: At least one character of the set, which represents the local part
        @: The at sign
        [a-zA-Z0-9.-]+: At least one character of the set, which represents the domain
        \\.: The dot, which is because \ itself is an escape character
        [a-zA-Z]{2,}: At least two characters of the set, which represents the top-level domain
     */
    CHECK (
      Email REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$'
    )
);

CREATE TABLE Enrolment(
    StudentID VARCHAR(15) PRIMARY KEY,
    ID VARCHAR(18) UNIQUE NOT NULL,
    EnrolmentDate DATE NOT NULL DEFAULT (CURDATE()),
    Major VARCHAR(50) NOT NULL,
    -- Define the constraints here
    /*
        StudentID: 10 characters: 2 uppercase letters followed by 8 digits
            Undergraduate: PB, JL, PL
            Graduate: BA, BE, BZ, SA, SB, SM, SF, BJ, BC, SC, BL, SL, CB, CS
    */
    CHECK (
        StudentID REGEXP '^(PB|JL|PL|BA|BE|BZ|SA|SB|SM|SF|BJ|BC|SC|BL|SL|CB|CS)[0-9]{8}$'
    ),
    -- ID: A foreign key that references Student.ID
    FOREIGN KEY (ID) REFERENCES Student(ID),
    /*
        Major:
        '数学与应用数学', '信息与计算科学',
        '物理学', '应用物理学', '天文学'， ‘光电信息科学与工程’,
        '工商管理', '信息管理与信息系统', '管理科学', '金融学', '统计学',
        '化学', '材料物理', '材料化学', '高分子材料与工程',
        '地球物理学', '大气科学', '空间科学与技术',
        '地球化学', '行星科学', '环境科学',
        '理论与应用力学', '机械设计制造及其自动化', '测控技术与仪器', '能源与动力工程', '安全工程',
        '英语', '考古学', '传播学', '网络与新媒体',
        '核工程与核技术', '工程物理', '应用物理学',
        '环境科学与工程',
        '量子信息科学',
        '生物科学', '生物技术',
        '临床医学',
        '电子信息工程', '通信工程',
        '自动化', '人工智能',
        '计算机科学与技术', '软件工程',
        '电子科学与技术',
        '信息安全', '网络空间安全',
        '数据科学与大数据技术'
    */
    CHECK (
        Major IN (
            '数学与应用数学', '信息与计算科学',
            '物理学', '应用物理学', '天文学', '光电信息科学与工程',
            '工商管理', '信息管理与信息系统', '管理科学', '金融学', '统计学',
            '化学', '材料物理', '材料化学', '高分子材料与工程',
            '地球物理学', '大气科学', '空间科学与技术',
            '地球化学', '行星科学', '环境科学',
            '理论与应用力学', '机械设计制造及其自动化', '测控技术与仪器', '能源与动力工程', '安全工程',
            '英语', '考古学', '传播学', '网络与新媒体',
            '核工程与核技术', '工程物理', '应用物理学',
            '环境科学与工程',
            '量子信息科学',
            '生物科学', '生物技术',
            '临床医学',
            '电子信息工程', '通信工程',
            '自动化', '人工智能',
            '计算机科学与技术', '软件工程',
            '电子科学与技术',
            '信息安全', '网络空间安全',
            '数据科学与大数据技术'
          )
    )
);

CREATE TABLE Course(
    CourseID VARCHAR(20) PRIMARY KEY,
    CourseName VARCHAR(50) NOT NULL,
    Credits DECIMAL(3, 1) NOT NULL,         -- DECIMAL(3, 1) means 3 digits in total, 1 of which is after the decimal point
    -- Define the constraints here
    -- Course ID only contains letters and digits
    CHECK (
        CourseID REGEXP '^[A-Za-z0-9]{1,20}$'
    ),
    -- Credits is n * 0.5
    CHECK(
        MOD(Credits * 10, 5) = 0
    )
);

CREATE TABLE Grade (
    StudentID VARCHAR(15),
    CourseID VARCHAR(20),
    Score INT,
    -- Define the constraints here
    -- Primary Key: StudentID and CourseID
    PRIMARY KEY (StudentID, CourseID),
    -- Student ID: A foreign key that references Student.ID
    FOREIGN KEY (StudentID) REFERENCES Enrolment(StudentID),
    -- Course ID: A foreign key that references Course.CourseID
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
    -- Score: 0-100
    CHECK (
        Score >= 0 AND Score <= 100
    )
);

CREATE TABLE Award (
    AwardName VARCHAR(50),
    StudentID VARCHAR(15),
    AwardLevel VARCHAR(10) NOT NULL,
    AwardDate DATE NOT NULL DEFAULT (CURDATE()),
    -- Define the constraints here
    -- Primary Key: (AwardName, StudentID)
    PRIMARY KEY (AwardName, StudentID),
    -- Student ID: A foreign key that references Student.ID
    FOREIGN KEY (StudentID) REFERENCES Enrolment(StudentID),
    /*
        Level: Chosen from '院级', '校级', '市级', '省级', '国家级'
    */
    CHECK (
        AwardLevel IN ('院级', '校级', '市级', '省级', '国家级')
    )
);

CREATE TABLE Punishment (
    PunishmentName VARCHAR(50),
    StudentID VARCHAR(15),
    PunishmentDate DATE NOT NULL DEFAULT (CURDATE()),
    -- Define the constraints here
    -- Primary Key: (PunishmentName, StudentID)
    PRIMARY KEY (PunishmentName, StudentID),
    -- Student ID: A foreign key that references Student.ID
    FOREIGN KEY (StudentID) REFERENCES Enrolment(StudentID)
);