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
        -- Status: '通过' if Score >= 60, '未通过' otherwise
        IF(Grade.Score >= 60, '通过', '未通过') AS Status
    FROM Course, Grade
    WHERE Course.CourseID = Grade.CourseID;

-- Student Info: Student ID, Student Name, Student Gender
CREATE VIEW StudentInfo AS
    SELECT
        Enrolment.StudentID,
        Student.Name,
        Student.Gender
    FROM Student, Enrolment
    WHERE Student.ID = Enrolment.ID;