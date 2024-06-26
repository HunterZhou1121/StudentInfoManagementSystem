USE lab02;
-- Truncate the tables
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Grade;
TRUNCATE TABLE Course;
TRUNCATE TABLE Award;
TRUNCATE TABLE Punishment;
TRUNCATE TABLE StudentAccount;
TRUNCATE TABLE Enrolment;
TRUNCATE TABLE Student;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert into Student
INSERT INTO
    Student(
        ID, Name, Gender, DOB, Ethnicity, PoliticalAffiliation
    )
VALUES
    ('123456200211212333', 'Ezra Bridger', '男', '2002-11-21', '汉族', '共青团员'),
    ('234567200301014566', 'Ahsoka Tano', '女', '2003-01-01', '汉族', '中共党员'),
    ('135246200402021357', 'Sabine Wren', '女', '2004-02-02', '满族', '中共预备党员');
-- Insert into Enrolment
INSERT INTO
    Enrolment(
        StudentID, ID, EnrolmentDate, Major
    )
VALUES
    ('PB21111738', '123456200211212333', '2021-09-01', '计算机科学与技术'),
    ('PB22011111', '234567200301014566', '2022-09-01', '数学与应用数学'),
    ('PB23021112', '135246200402021357', '2023-09-01', '物理学');

-- Insert into Course
INSERT INTO
    Course(
        CourseID, CourseName, Credits
    )
VALUES
    -- General Computer Courses
    ('CS1001A', '计算机程序设计A', 4.0),
    ('CS1002A', '计算系统概论A', 4.0),
    -- General Mathematical Courses
    ('MATH1006', '数学分析(B1)', 6.0),
    ('MATH1007', '数学分析(B2)', 6.0),
    ('MATH1009', '线性代数(B1)', 4.0),
    -- General Physics Courses
    ('PHYS1001B', '力学B', 2.5),
    ('PHYS1002B', '热学B', 1.5),
    ('PHYS1004C', '电磁学C', 3.0),
    ('PHYS1010', '量子物理', 3.0),
    -- Major Foundation Courses
    ('011040', '图论', 3.0),
    ('011103', '代数结构', 3.0),
    ('011151', '模拟与数字电路', 4.0),
    ('CS2002', '数理逻辑基础', 2.0),
    ('STAT2002', '概率论与数理统计', 3.0),
    -- Major Core Courses
    ('011127', '数据结构', 3.0),
    ('011144', '计算机网络', 3.5),
    ('011145', '计算机组成原理', 4.0),
    ('011146', '算法基础', 3.5),
    ('011147', '数据库系统及应用', 3.5),
    ('011163', '编译原理和技术', 4.0),
    ('011174', '操作系统原理与设计', 4.0),
    -- Major Elective Courses
    ('011182', '嵌入式系统设计方法', 2.5),
    ('CS4014', '计算机体系结构', 4.0),
    ('CS4019', '计算系统综合实践', 2.0),
    ('CS4022', '智能计算系统基础', 3.0),
    ('011044', '计算机导论', 1.0),
    ('CS4009', '程序设计进阶与实践', 3.0),
    ('011186', '大数据算法', 3.0),
    ('COMP5001P', '程序语言设计与程序分析', 3.5),
    ('CS2001', '软件工程导论', 2.0),
    ('CS4005', '形式化方法导引', 3.0),
    ('CS4008', '软件工程实践', 2.5),
    ('CS4010', '数理逻辑进阶与应用', 2.0),
    ('CS4011', '算法博弈论', 2.5),
    ('CS4012', '运筹学基础', 2.5),
    ('CS4017', '最优化导论', 2.0),
    ('CS4021', '软件综合实验', 1.0),
    ('010009', '模式识别导论', 2.0),
    ('011179', 'Web信息处理与应用', 3.5),
    ('011172', '网络系统实验', 1.0),
    ('011184', '信息安全导论', 3.0),
    ('011185', '网络算法学', 3.0),
    ('011187', '数据隐私的方法伦理和实践', 3.5),
    ('CS4004', '区块链技术与应用', 2.5),
    ('CS4016e', '信息安全导论(英)', 3.0),
    ('CS4018', '量子计算与机器学习', 3.5),
    ('DS2001', '深度学习导论', 3.0),
    ('011170', '并行计算', 2.5);

-- Insert into Grade
INSERT INTO
    Grade(
        StudentID, CourseID, Score
    )
VALUES
    ('PB21111738', 'CS1001A', 89),  -- 计算机程序设计A
    ('PB21111738', '011144', 86),   -- 计算机网络
    ('PB21111738', 'CS4009', 93),   -- 程序设计进阶与实践
    ('PB21111738', '011187', NULL),   -- 数据隐私的方法伦理和实践
    ('PB22011111', 'MATH1006', 55), -- 数学分析(B1)
    ('PB22011111', '011040', 88),   -- 图论
    ('PB22011111', '011127', 88),   -- 数据结构
    ('PB22011111', 'CS4014', 90),   -- 计算机体系结构
    ('PB22011111', 'CS4018', NULL),   -- 量子计算与机器学习
    ('PB23021112', 'PHYS1001B', 59),-- 力学B
    ('PB23021112', '011103', 89),   -- 代数结构
    ('PB23021112', '011151', 58),   -- 模拟与数字电路
    ('PB23021112', 'PHYS1010', NULL), -- 量子物理
    ('PB21111738', '011127', 80);   -- 数据结构
-- Insert into Award
INSERT INTO
    Award(
        StudentID, AwardName, AwardLevel, AwardDate
    )
VALUES
    ('PB21111738', '优秀社团骨干', '校级', '2021-12-01'),
    ('PB22011111', '院学生会优秀骨干', '院级', '2022-12-01'),
    ('PB23021112', '大学生数学竞赛一等奖', '省级', '2023-12-01');

-- Insert into Punishment
INSERT INTO
    Punishment(
        StudentID, PunishmentName, PunishmentDate
    )
VALUES
    ('PB21111738', '违反校规', '2021-12-02'),
    ('PB22011111', '违反校规', '2022-12-02'),
    ('PB23021112', '违反校规', '2023-12-02');


