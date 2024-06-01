/*
    Student Info
        Student Operations: Query and Update
        Teacher Operations: Insert, Delete, Update and Query
*/
DELIMITER //

DROP PROCEDURE IF EXISTS DeleteStudent;
DROP PROCEDURE IF EXISTS UpdateStudentID;
DROP PROCEDURE IF EXISTS DeleteCourse;
DROP PROCEDURE IF EXISTS UpdateCourseID;

CREATE PROCEDURE DeleteStudent(IN StudentID VARCHAR(15))
BEGIN
    DECLARE StudentIDExists BOOLEAN;
    DECLARE IDToDelete VARCHAR(18);
#     -- Find the student from Enrolment
#     DECLARE CONTINUE HANDLER FOR NOT FOUND
#     BEGIN
#         SET StudentIDExists = FALSE;
#     END;
    -- Use transaction to ensure atomicity
    START TRANSACTION;
    -- Find the student from Enrolment
    SELECT EXISTS(
        SELECT 1
            FROM Enrolment
            WHERE Enrolment.StudentID = StudentID
    ) INTO StudentIDExists;
    -- Find ID of the Student
    SELECT ID INTO IDToDelete
        FROM Enrolment
        WHERE Enrolment.StudentID = StudentID;
    -- If the student exists
    IF StudentIDExists THEN
        SET SQL_SAFE_UPDATES = 0;
        DELETE FROM Grade WHERE Grade.StudentID = StudentID;
        DELETE FROM Award WHERE Award.StudentID = StudentID;
        DELETE FROM Punishment WHERE Punishment.StudentID = StudentID;
        DELETE FROM StudentAccount WHERE StudentUsername = StudentID;
        DELETE FROM Enrolment WHERE Enrolment.StudentID = StudentID;

        DELETE FROM Student WHERE Student.ID = IDToDelete;
        SET SQL_SAFE_UPDATES = 1;
        COMMIT;
        SELECT 'Student deleted successfully.' as Message;
    ELSE
        ROLLBACK;
        SELECT 'Student ID does not exist.' as Error;
    END IF;

END //

-- Update StudentID
CREATE PROCEDURE UpdateStudentID(IN OldStudentID VARCHAR(15), IN NewStudentID VARCHAR(15))
BEGIN
    DECLARE OldStudentIDExists BOOLEAN;
    DECLARE NewStudentIDExists BOOLEAN;
    START TRANSACTION;
    -- See if the old student ID exists
    SELECT EXISTS(
        SELECT 1
            FROM Enrolment
            WHERE Enrolment.StudentID = OldStudentID
    ) INTO OldStudentIDExists;
    -- See if the new student ID exists
    SELECT EXISTS(
        SELECT 1
            FROM Enrolment
            WHERE Enrolment.StudentID = NewStudentID
    ) INTO NewStudentIDExists;
    IF OldStudentIDExists THEN
        IF NewStudentIDExists = FALSE THEN
            -- Backup related data: Grade, Award, Punishment, StudentAccount
            SET SQL_SAFE_UPDATES = 0;
            -- Grade
            CREATE TEMPORARY TABLE GradeBackup AS
                SELECT *
                    FROM Grade
                    WHERE Grade.StudentID = OldStudentID;
            UPDATE GradeBackup
                SET StudentID = NewStudentID;
            DELETE FROM Grade WHERE Grade.StudentID = OldStudentID;
            -- Award
            CREATE TEMPORARY TABLE AwardBackup AS
                SELECT *
                    FROM Award
                    WHERE Award.StudentID = OldStudentID;
            UPDATE AwardBackup
                SET StudentID = NewStudentID;
            DELETE FROM Award WHERE Award.StudentID = OldStudentID;
            -- Punishment
            CREATE TEMPORARY TABLE PunishmentBackup AS
                SELECT *
                    FROM Punishment
                    WHERE Punishment.StudentID = OldStudentID;
            UPDATE PunishmentBackup
                SET StudentID = NewStudentID;
            DELETE FROM Punishment WHERE Punishment.StudentID = OldStudentID;
            -- StudentAccount
            CREATE TEMPORARY TABLE StudentAccountBackup AS
                SELECT *
                    FROM StudentAccount
                    WHERE StudentAccount.StudentUsername = OldStudentID;
            UPDATE StudentAccountBackup
                SET StudentUsername = NewStudentID;
            DELETE FROM StudentAccount WHERE StudentAccount.StudentUsername = OldStudentID;

            SET SQL_SAFE_UPDATES = 1;

            -- Update Enrolment
            UPDATE Enrolment
                SET StudentID = NewStudentID
                WHERE Enrolment.StudentID = OldStudentID;
            -- Insert the updated data
            INSERT INTO Grade
                SELECT *
                    FROM GradeBackup;
            INSERT INTO Award
                SELECT *
                    FROM AwardBackup;
            INSERT INTO Punishment
                SELECT *
                    FROM PunishmentBackup;
            INSERT INTO StudentAccount
                SELECT *
                    FROM StudentAccountBackup;
            -- Delete the temporary tables
            DROP TEMPORARY TABLE GradeBackup;
            DROP TEMPORARY TABLE AwardBackup;
            DROP TEMPORARY TABLE PunishmentBackup;
            DROP TEMPORARY TABLE StudentAccountBackup;

            COMMIT;
            SELECT 'Student ID updated successfully.' as Message;
        ELSE
            ROLLBACK;
            SELECT 'New Student ID already exists.' as Error;
        END IF;
    ELSE
        ROLLBACK;
        SELECT 'Old Student ID does not exist.' as Error;
    END IF;
END //

-- Delete Course
CREATE PROCEDURE DeleteCourse(IN CourseID VARCHAR(20))
BEGIN
    DECLARE CourseIDExists BOOLEAN;
    START TRANSACTION;
    SELECT EXISTS(
        SELECT 1
            FROM Course
            WHERE Course.CourseID = CourseID
    ) INTO CourseIDExists;
    IF CourseIDExists THEN
        SET SQL_SAFE_UPDATES = 0;
        DELETE FROM Grade WHERE Grade.CourseID = CourseID;
        DELETE FROM Course WHERE Course.CourseID = CourseID;
        SET SQL_SAFE_UPDATES = 1;
        COMMIT;
        SELECT 'Course deleted successfully.' as Message;
    ELSE
        ROLLBACK;
        SELECT 'Course ID does not exist.' as Error;
    END IF;
END //

-- Update Course ID
CREATE PROCEDURE UpdateCourseID(IN OldCourseID VARCHAR(20), IN NewCourseID VARCHAR(20))
BEGIN
    DECLARE OldCourseIDExists BOOLEAN;
    DECLARE NewCourseIDExists BOOLEAN;
    START TRANSACTION;
    -- See if the old course ID exists
    SELECT EXISTS(
        SELECT 1
            FROM Course
            WHERE Course.CourseID = OldCourseID
    ) INTO OldCourseIDExists;
    -- See if the new course ID exists
    SELECT EXISTS(
        SELECT 1
            FROM Course
            WHERE Course.CourseID = NewCourseID
    ) INTO NewCourseIDExists;
    IF OldCourseIDExists THEN
        IF NewCourseIDExists = FALSE THEN
            -- Backup related data: Grade
            SET SQL_SAFE_UPDATES = 0;
            -- Grade
            CREATE TEMPORARY TABLE GradeBackup AS
                SELECT *
                    FROM Grade
                    WHERE Grade.CourseID = OldCourseID;
            UPDATE GradeBackup
                SET CourseID = NewCourseID;
            DELETE FROM Grade WHERE Grade.CourseID = OldCourseID;
            SET SQL_SAFE_UPDATES = 1;

            -- Update Course
            UPDATE Course
                SET CourseID = NewCourseID
                WHERE Course.CourseID = OldCourseID;
            -- Insert the updated data
            INSERT INTO Grade
                SELECT *
                    FROM GradeBackup;
            -- Delete the temporary tables
            DROP TEMPORARY TABLE GradeBackup;

            COMMIT;
            SELECT 'Course ID updated successfully.' as Message;
        ELSE
            ROLLBACK;
            SELECT 'New Course ID already exists.' as Error;
        END IF;
    ELSE
        ROLLBACK;
        SELECT 'Old Course ID does not exist.' as Error;
    END IF;
END //

DELIMITER ;

# -- Test
# CALL DeleteStudent('PB22011111');
#
# -- Failure
# CALL DeleteStudent('PB21011111');
#
# -- Test
# CALL UpdateStudentID('PB21111738', 'PB22111738');
# CALL UpdateStudentID('PB22111738', 'PB21111738');
-- [2024-06-01 20:40:43] [HY000][3819] Check constraint 'enrolment_chk_1' is violated.
# CALL UpdateStudentID('PB21111738', '123456');
#
# -- Failure
# CALL UpdateStudentID('PB21011111', 'PB21111738'); -- Old Student ID does not exist
# CALL UpdateStudentID('PB21111738', 'PB23021112'); -- New Student ID already exists
#
# -- Test
# CALL DeleteCourse('011103'); -- 代数结构
#
# -- Failure
# CALL DeleteCourse('011103'); -- Course ID does not exist
#
# -- Test
# CALL UpdateCourseID('011127', '011128');
#
# -- Failure
# CALL UpdateCourseID('011127', '011103'); -- Old Course ID does not exist
# CALL UpdateCourseID('011128', '011144'); -- New Course ID already exists


