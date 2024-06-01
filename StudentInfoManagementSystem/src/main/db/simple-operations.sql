-- Here are simple operations that do not require procedures or functions.

-- Query basic info of a student with StudentID
SELECT * FROM StudentInfo WHERE StudentID = 'PB21111738';
-- StudentID does not exist: No error will be thrown, the result will be empty
SELECT * FROM StudentInfo WHERE StudentID = 'PB11111738';

-- Update PhotoURL of a student with StudentID
UPDATE Student SET PhotoURL = 'https://example.com/photo.jpg' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111738');
-- StudentID does not exist: No error will be thrown, use affectedRow = ps.executeUpdate() to check if the update is successful
UPDATE Student SET PhotoURL = 'https://example.com/photo.jpg' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB11111738');

-- Update PhoneNumber of a student with StudentID
UPDATE Student SET PhoneNumber = '12345678901' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111738');
-- Violate the constraint of PhoneNumber: Error will be thrown [HY000][3819] Check constraint 'student_chk_3' is violated.
UPDATE Student SET PhoneNumber = '1' WHERE ID IN
     (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111738');

-- Update Email of a student with StudentID
UPDATE Student SET Email = 'ziyu_zhou2002@mail.ustc.edu.cn' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111738');
-- Violate the constraint of Email: Error will be thrown [HY000][3819] Check constraint 'student_chk_4' is violated.
UPDATE Student SET Email = 'ziyu_zhou2002mail.ustc.edu.cn' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111738');

-- Update DOB of a student with StudentID
UPDATE Student SET DOB = '2002-01-01' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111738');
UPDATE Student SET DOB = '2002-11-21' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111738');
-- [45000][1644] DOB cannot be later than the current date.
UPDATE Student SET DOB = '2077-11-21' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111738');

-- Insert Student: ID, Name, Gender, DOB, Ethnicity, PoliticalAffiliation, StudentID, EnrolmentDate, Major
INSERT INTO
    Student(
        ID, Name, Gender, DOB, Ethnicity, PoliticalAffiliation
    )
VALUES
    ('111111200001011111', 'Hera Syndulla', '女', '2000-01-01', '汉', '中共预备党员');
INSERT INTO
    Enrolment(
        StudentID, ID, EnrolmentDate, Major
    )
VALUES
    ('PB21111739', '111111200001011111', '2021-09-01', '计算机科学与技术');
-- ID already exists: Error will be thrown [23000][1062] Duplicate entry '111111200001011111' for key 'student.PRIMARY'
-- Re-run the last two inserts
-- Violate the constraint: [HY000][3819] Check constraint 'student_chk_1' is violated.
INSERT INTO
    Student(
    ID, Name, Gender, DOB, Ethnicity, PoliticalAffiliation
)
VALUES
    ('211111200001011111', 'Jacen Syndulla', 'Male', '2000-01-01', '汉', '中共预备党员');
-- [HY000][3819] Check constraint 'student_chk_2' is violated.
INSERT INTO
    Student(
    ID, Name, Gender, DOB, Ethnicity, PoliticalAffiliation
)
VALUES
    ('211111200001011111', 'Jacen Syndulla', '男', '2000-01-01', '汉', '未知');

DELETE FROM StudentAccount WHERE StudentAccount.StudentUsername = 'PB21111739';
DELETE FROM Enrolment WHERE Enrolment.ID = '111111200001011111';
-- [45000][1644] EnrolmentDate cannot be later than the current date.
INSERT INTO
    Enrolment(
    StudentID, ID, EnrolmentDate, Major
)
VALUES
    ('PB21111739', '111111200001011111', '2077-09-01', '计算机科学与技术');
-- [45000][1644] EnrolmentDate cannot be earlier than the student's DOB.
INSERT INTO
    Enrolment(
    StudentID, ID, EnrolmentDate, Major
)
VALUES
    ('PB21111739', '111111200001011111', '1997-09-01', '计算机科学与技术');

-- Update Name of a student with StudentID
UPDATE Student SET Name = 'General Hera Syndulla' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111739');

-- Update DOB of a student with StudentID
UPDATE Student SET DOB = '2000-01-02' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111739');
-- [45000][1644] DOB cannot be later than the current date.
UPDATE Student SET DOB = '2077-01-01' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111739');

-- Update Gender of a student with StudentID
UPDATE Student SET Gender = '男' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111739');
-- [HY000][3819] Check constraint 'student_chk_1' is violated.
UPDATE Student SET Gender = 'MALE' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111739');

-- Update Ethnicity of a student with StudentID
UPDATE Student SET Ethnicity = '满' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111739');

-- Update PoliticalAffiliation of a student with StudentID
UPDATE Student SET PoliticalAffiliation = '中共党员' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111739');
-- [HY000][3819] Check constraint 'student_chk_2' is violated.
UPDATE Student SET PoliticalAffiliation = '党员' WHERE ID IN
    (SELECT ID FROM Enrolment WHERE StudentID = 'PB21111739');

-- Query major of a student with StudentID
SELECT Major FROM StudentInfo WHERE StudentID = 'PB21111738';

-- Update Major of a student with StudentID
UPDATE Enrolment SET Major = '软件工程' WHERE StudentID = 'PB21111738';
-- StudentID does not exist: No error will be thrown, use affectedRow = ps.executeUpdate() to check if the update is successful
UPDATE Enrolment SET Major = '软件工程' WHERE StudentID = 'PB11111738';
-- Violate the constraint of Major: Error will be thrown [HY000][3819] Check constraint 'enrolment_chk_2' is violated.
UPDATE Enrolment SET Major = '计算机' WHERE StudentID = 'PB21111738';

-- Query the award of a student with StudentID
SELECT
    StudentInfo.StudentID, StudentInfo.Name, AwardName, AwardLevel, AwardDate
FROM Award, StudentInfo
WHERE StudentInfo.StudentID = Award.StudentID AND StudentInfo.StudentID = 'PB21111738';

-- Insert Award to a student with StudentID
INSERT INTO
    Award(
        StudentID, AwardName, AwardLevel, AwardDate
    )
VALUES
    ('PB21111738', '2021校级优秀学生', '校级', '2021-12-31');

-- Try with an invalid date: [45000][1644] AwardDate cannot be later than the current date.
INSERT INTO
    Award(
        StudentID, AwardName, AwardLevel, AwardDate
    )
VALUES
    ('PB21111738', '2077校级优秀学生', '校级', '2077-12-31');
-- Try with an invalid date: [45000][1644] AwardDate cannot be earlier than EnrolmentDate.
INSERT INTO
    Award(
        StudentID, AwardName, AwardLevel, AwardDate
    )
VALUES
    ('PB21111738', '1997校级优秀学生', '校级', '1997-12-31');
-- Try with an invalid AwardLevel: [HY000][3819] Check constraint 'award_chk_1' is violated.
INSERT INTO
    Award(
        StudentID, AwardName, AwardLevel, AwardDate
    )
VALUES
    ('PB21111738', '诺贝尔奖', '全球级', '2023-12-31');
-- Delete Award of a student with StudentID
DELETE FROM Award WHERE StudentID = 'PB21111738' AND AwardName = '2021市级优秀学生';

-- Update AwardName of a student with StudentID
UPDATE Award SET AwardName = '2021市级优秀学生' WHERE StudentID = 'PB21111738' AND AwardName = '2021校级优秀学生';

-- Update AwardLevel of a student with StudentID
UPDATE Award SET AwardLevel = '市级' WHERE StudentID = 'PB21111738' AND AwardName = '2021市级优秀学生';

# -- Update AwardName and AwardLevel of a student with StudentID
# UPDATE Award SET AwardName = '2021市级优秀学生', AwardLevel = '市级' WHERE StudentID = 'PB21111738' AND AwardName = '2021校级优秀学生';

-- Update AwardDate of a student with StudentID
UPDATE Award SET AwardDate = '2021-09-01' WHERE StudentID = 'PB21111738' AND AwardName = '2021市级优秀学生';

-- [45000][1644] AwardDate cannot be later than the current date.
UPDATE Award SET AwardDate = '2077-09-01' WHERE StudentID = 'PB21111738' AND AwardName = '2021市级优秀学生';

-- [45000][1644] AwardDate cannot be earlier than EnrolmentDate.
UPDATE Award SET AwardDate = '1997-09-01' WHERE StudentID = 'PB21111738' AND AwardName = '2021市级优秀学生';

-- Query the punishment of a student with StudentID
SELECT
    StudentInfo.StudentID, StudentInfo.Name, PunishmentName, PunishmentDate
FROM Punishment, StudentInfo
WHERE StudentInfo.StudentID = Punishment.StudentID AND StudentInfo.StudentID = 'PB21111738';

-- Insert Punishment to a student with StudentID
INSERT INTO
    Punishment(
        StudentID, PunishmentName, PunishmentDate
    )
VALUES
    ('PB21111738', '迟到', '2021-12-31');

-- Re-run: [23000][1062] Duplicate entry '迟到-PB21111738' for key 'punishment.PRIMARY'

-- [45000][1644] PunishmentDate cannot be later than the current date.
INSERT INTO
    Punishment(
    StudentID, PunishmentName, PunishmentDate
)
VALUES
    ('PB21111738', '早退', '2077-12-31');

-- [45000][1644] PunishmentDate cannot be earlier than EnrolmentDate.
INSERT INTO
    Punishment(
    StudentID, PunishmentName, PunishmentDate
)
VALUES
    ('PB21111738', '早退', '1997-12-31');

-- Delete Punishment of a student with StudentID
DELETE FROM Punishment WHERE StudentID = 'PB21111738' AND PunishmentName = '迟到';

-- Update PunishmentName of a student with StudentID
UPDATE Punishment SET PunishmentName = '缺课' WHERE StudentID = 'PB21111738' AND PunishmentName = '违反校规';

-- Update PunishmentDate of a student with StudentID
UPDATE Punishment SET PunishmentDate = '2022-01-01' WHERE StudentID = 'PB21111738' AND PunishmentName = '缺课';

-- [45000][1644] PunishmentDate cannot be later than the current date.
UPDATE Punishment SET PunishmentDate = '2077-01-01' WHERE StudentID = 'PB21111738' AND PunishmentName = '缺课';

-- [45000][1644] PunishmentDate cannot be earlier than EnrolmentDate.
UPDATE Punishment SET PunishmentDate = '1997-01-01' WHERE StudentID = 'PB21111738' AND PunishmentName = '缺课';

-- Query the current courses (that is, with null score) of a student with StudentID
SELECT
    CourseName, CourseID, Credits
FROM StudentGrade
WHERE StudentGrade.StudentID = 'PB21111738' and StudentGrade.Score IS NULL;

-- Add a new course
INSERT INTO
    Course(
        CourseID, CourseName, Credits
    )
VALUES
    ('011111', '新课程', 3.0);
-- [HY000][3819] Check constraint 'course_chk_1' is violated.
INSERT INTO
    Course(
        CourseID, CourseName, Credits
    )
VALUES
    ('011112+', '新课程', 3.0);
-- [HY000][3819] Check constraint 'course_chk_2' is violated.
INSERT INTO
    Course(
        CourseID, CourseName, Credits
    )
VALUES
    ('011113', '新课程', 3.7);

-- Add a course for a student
INSERT INTO
    Grade(
        StudentID, CourseID, Score
    )
VALUES
    ('PB21111738', '011111', NULL);

-- Delete a course for a student with StudentID
DELETE FROM Grade WHERE StudentID = 'PB21111738' AND CourseID = '011111';

-- Update a course name
UPDATE Course SET CourseName = '新课程2' WHERE CourseID = '011111';

-- Update a course credits
UPDATE Course SET Credits = 3.5 WHERE CourseID = '011111';
-- [HY000][3819] Check constraint 'course_chk_2' is violated.
UPDATE Course SET Credits = 3.7 WHERE CourseID = '011111';

-- Query the grades of a student with StudentID
SELECT
    CourseName, CourseID, Credits, Score, Status
FROM StudentGrade
WHERE StudentGrade.StudentID = 'PB21111738' and StudentGrade.Score IS NOT NULL;

-- Update the score of a student with StudentID
UPDATE Grade SET Score = 60 WHERE StudentID = 'PB21111738' AND CourseID = '011111';





