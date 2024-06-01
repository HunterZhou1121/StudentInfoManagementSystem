DELIMITER //

DROP TRIGGER IF EXISTS CreateStudentAccount;
DROP TRIGGER IF EXISTS BeforeStudentInsert;
DROP TRIGGER IF EXISTS BeforeStudentUpdate;
DROP TRIGGER IF EXISTS BeforeEnrolmentInsert;
DROP TRIGGER IF EXISTS BeforeEnrolmentUpdate;
DROP TRIGGER IF EXISTS BeforeAwardInsert;
DROP TRIGGER IF EXISTS BeforeAwardUpdate;
DROP TRIGGER IF EXISTS BeforePunishmentInsert;
DROP TRIGGER IF EXISTS BeforePunishmentUpdate;


-- Trigger to automatically create student account
CREATE TRIGGER CreateStudentAccount AFTER INSERT ON Enrolment
    FOR EACH ROW
BEGIN
    --  New StudentAccount: NEW.StudentID, 123456
    INSERT INTO
        StudentAccount(StudentUsername)
    VALUES (NEW.StudentID);
END //

-- Triggers to make sure DOB is no later than the current date
CREATE TRIGGER BeforeStudentInsert BEFORE INSERT ON Student
    FOR EACH ROW
BEGIN
    -- Make sure that DOB is no later than the current date
    IF NEW.DOB > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'DOB cannot be later than the current date.';
    END IF;
END //

CREATE TRIGGER BeforeStudentUpdate BEFORE UPDATE ON Student
    FOR EACH ROW
BEGIN
    -- Make sure that DOB is no later than the current date
    IF NEW.DOB > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'DOB cannot be later than the current date.';
    END IF;
END //

-- Triggers to make sure EnrolmentDate is no later than the current date and no earlier than the student's DOB
CREATE TRIGGER BeforeEnrolmentInsert BEFORE INSERT ON Enrolment
    FOR EACH ROW
BEGIN
    -- Get DOB
    DECLARE DOB DATE;
    SELECT Student.DOB
        INTO DOB
    FROM Student
    WHERE Student.ID = NEW.ID;
    -- Make sure that EnrolmentDate is no later than the current date and no earlier than the student's DOB
    IF NEW.EnrolmentDate > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'EnrolmentDate cannot be later than the current date.';
    ELSEIF NEW.EnrolmentDate < DOB THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'EnrolmentDate cannot be earlier than the student''s DOB.';
    END IF;
END //

CREATE TRIGGER BeforeEnrolmentUpdate BEFORE UPDATE ON Enrolment
    FOR EACH ROW
BEGIN
    -- Get DOB
    DECLARE DOB DATE;
    SELECT Student.DOB
        INTO DOB
    FROM Student
    WHERE Student.ID = NEW.ID;
    -- Make sure that EnrolmentDate is no later than the current date and no earlier than the student's DOB
    IF NEW.EnrolmentDate > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'EnrolmentDate cannot be later than the current date.';
    ELSEIF NEW.EnrolmentDate < DOB THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'EnrolmentDate cannot be earlier than the student''s DOB.';
    END IF;
END //


-- Triggers to make sure AwardDate is no earlier than EnrolmentDate and no later than the current date
CREATE TRIGGER BeforeAwardInsert BEFORE INSERT ON Award
    FOR EACH ROW
BEGIN
    -- Get EnrolmentDate
    DECLARE EnrolmentDate DATE;
    SELECT Enrolment.EnrolmentDate
        INTO EnrolmentDate
    FROM Enrolment
    WHERE Enrolment.StudentID = NEW.StudentID;
    -- Make sure that AwardDate is no earlier than EnrolmentDate and no later than the current date
    IF NEW.AwardDate < EnrolmentDate THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'AwardDate cannot be earlier than EnrolmentDate.';
    ELSEIF NEW.AwardDate > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'AwardDate cannot be later than the current date.';
    END IF;
END //

CREATE TRIGGER BeforeAwardUpdate BEFORE UPDATE ON Award
    FOR EACH ROW
BEGIN
    -- Get EnrolmentDate
    DECLARE EnrolmentDate DATE;
    SELECT Enrolment.EnrolmentDate
        INTO EnrolmentDate
    FROM Enrolment
    WHERE Enrolment.StudentID = NEW.StudentID;
    -- Make sure that AwardDate is no earlier than EnrolmentDate and no later than the current date
    IF NEW.AwardDate < EnrolmentDate THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'AwardDate cannot be earlier than EnrolmentDate.';
    ELSEIF NEW.AwardDate > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'AwardDate cannot be later than the current date.';
    END IF;
END //

-- Triggers to make sure PunishmentDate is no earlier than EnrolmentDate and no later than the current date
CREATE TRIGGER BeforePunishmentInsert BEFORE INSERT ON Punishment
    FOR EACH ROW
BEGIN
    -- Get EnrolmentDate
    DECLARE EnrolmentDate DATE;
    SELECT Enrolment.EnrolmentDate
        INTO EnrolmentDate
    FROM Enrolment
    WHERE Enrolment.StudentID = NEW.StudentID;
    -- Make sure that PunishmentDate is no earlier than EnrolmentDate and no later than the current date
    IF NEW.PunishmentDate < EnrolmentDate THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'PunishmentDate cannot be earlier than EnrolmentDate.';
    ELSEIF NEW.PunishmentDate > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'PunishmentDate cannot be later than the current date.';
    END IF;
END //

CREATE TRIGGER BeforePunishmentUpdate BEFORE UPDATE ON Punishment
    FOR EACH ROW
BEGIN
    DECLARE EnrolmentDate DATE;
    SELECT Enrolment.EnrolmentDate
        INTO EnrolmentDate
    FROM Enrolment
    WHERE Enrolment.StudentID = NEW.StudentID;
    -- Make sure that PunishmentDate is no earlier than EnrolmentDate and no later than the current date
    IF NEW.PunishmentDate < EnrolmentDate THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'PunishmentDate cannot be earlier than EnrolmentDate.';
    ELSEIF NEW.PunishmentDate > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'PunishmentDate cannot be later than the current date.';
    END IF;
END //

DELIMITER ;

