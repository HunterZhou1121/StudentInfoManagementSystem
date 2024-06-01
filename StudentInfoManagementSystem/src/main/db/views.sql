DROP VIEW IF EXISTS StudentGrade;
DROP VIEW IF EXISTS StudentInfo;

-- Student Grade View: Student ID, Course Name, Course ID, Credits, Score, Status
CREATE VIEW StudentGrade AS
    SELECT
        Grade.StudentID,
        Course.CourseName,
        Course.CourseID,
        Course.Credits,
        Grade.Score,
        -- Status: null if score is null, '通过' if score >= 60, '未通过' otherwise
        CASE
            WHEN Grade.Score IS NULL THEN NULL
            WHEN Grade.Score >= 60 THEN '通过'
            ELSE '未通过'
        END AS Status
    FROM Course, Grade
    WHERE Course.CourseID = Grade.CourseID;

-- Student Info: Student ID, Student Name, Student Gender
CREATE VIEW StudentInfo AS
    SELECT
        Student.ID,
        Student.PhotoURL,
        Student.Name,
        Student.Gender,
        Student.DOB,
        Student.Ethnicity,
        Student.PoliticalAffiliation,
        Student.PhoneNumber,
        Student.Email,
        Enrolment.StudentID,
        Enrolment.EnrolmentDate,
        Enrolment.Major
    FROM Student, Enrolment
    WHERE Student.ID = Enrolment.ID;