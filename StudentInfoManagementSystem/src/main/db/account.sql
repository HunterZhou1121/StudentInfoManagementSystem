DROP TABLE IF EXISTS StudentAccount;
DROP TABLE IF EXISTS TeacherAccount;

-- Create table for student account
CREATE TABLE StudentAccount(
    StudentUsername VARCHAR(15) PRIMARY KEY,                -- Username, same as student ID
    StudentPassword VARCHAR(255) DEFAULT '123456' NOT NULL, -- Password, default is 123456
    -- Define the constraints here
    -- StudentUsername is a foreign key to Student
    FOREIGN KEY (StudentUsername) REFERENCES Enrolment(StudentID),
    -- StudentPassword is at least 6 characters long
    CHECK (
        LENGTH(StudentPassword) >= 6
    )
);

-- Create table for teacher account
CREATE TABLE TeacherAccount(
    TeacherUsername VARCHAR(15) PRIMARY KEY,                        -- Username, here only administrator
    TeacherPassword VARCHAR(255) DEFAULT 'administrator' NOT NULL,  -- Password, default is administrator
    -- Define the constraints here
    -- TeacherPassword is at least 6 characters long
    CHECK (
        LENGTH(TeacherPassword) >= 6
    )
);

INSERT INTO
    TeacherAccount(TeacherUsername, TeacherPassword)
VALUES ('administrator', 'administrator');
