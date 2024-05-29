DROP VIEW IF EXISTS StudentGrade;

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