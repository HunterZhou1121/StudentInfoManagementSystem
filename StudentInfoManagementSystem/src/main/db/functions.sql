DELIMITER //
DROP FUNCTION IF EXISTS GetPassedCredits;
DROP FUNCTION IF EXISTS GetFailedCourseNum;
DROP FUNCTION IF EXISTS GetFailedCredits;
DROP FUNCTION IF EXISTS GetWeightedAverageScore;
-- Function to get the passed credits of a student
CREATE FUNCTION GetPassedCredits(StudentID VARCHAR(15))
RETURNS DECIMAL(4, 1)   -- DECIMAL(4, 1) means 4 digits in total, 1 of which is after the decimal point
READS SQL DATA
BEGIN
    DECLARE EarnedCredits DECIMAL(4, 1);
    SELECT
        SUM(Credits)
    INTO EarnedCredits
    FROM StudentGrade
    WHERE StudentGrade.StudentID = StudentID AND StudentGrade.Status = '通过';

    RETURN IFNULL(EarnedCredits, 0);
END //

-- Function to get the number of failed courses of a student
CREATE FUNCTION GetFailedCourseNum(StudentID VARCHAR(15))
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE FailedCourseNum INT;
    SELECT
        COUNT(*)
    INTO FailedCourseNum
    FROM StudentGrade
    WHERE StudentGrade.StudentID = StudentID AND StudentGrade.Status = '未通过';

    RETURN FailedCourseNum;
END //

-- Function to get the failed credits of a student
CREATE FUNCTION GetFailedCredits(StudentID VARCHAR(15))
RETURNS DECIMAL(4, 1)
READS SQL DATA
BEGIN
    DECLARE FailedCredits DECIMAL(4, 1);
    SELECT
        SUM(Credits)
    INTO FailedCredits
    FROM StudentGrade
    WHERE StudentGrade.StudentID = StudentID AND StudentGrade.Status = '未通过';

    RETURN IFNULL(FailedCredits, 0);
END //

-- Function to calculate the weighted average score of a student
CREATE FUNCTION GetWeightedAverageScore(StudentID VARCHAR(15))
RETURNS Float
READS SQL DATA
BEGIN
    DECLARE WeightedAverageScore Float;
    DECLARE TotalCredits DECIMAL(4, 1);
    DECLARE TotalWeightedScore Float;
    SELECT
        SUM(Credits), SUM(Credits * Score)
    INTO TotalCredits, TotalWeightedScore
    FROM StudentGrade
    WHERE StudentGrade.StudentID = StudentID and StudentGrade.Score IS NOT NULL;
    IF TotalCredits > 0 THEN
        SET WeightedAverageScore = TotalWeightedScore / TotalCredits;
    ELSE
        SET WeightedAverageScore = NULL;
    END IF;
    Set WeightedAverageScore = ROUND(WeightedAverageScore, 2);
    RETURN WeightedAverageScore;
END //

DELIMITER ;

# -- Test
# -- GetPassedCredits
# SELECT GetPassedCredits('PB21111738') as PassedCredits; -- 3.5+4.0+3.0=10.5
# SELECT GetPassedCredits('PB22011111') as PassedCredits; -- 3.0+3.0+4.0=10.0
# SELECT GetPassedCredits('PB23021112') as PassedCredits; -- 3.0
# UPDATE Grade SET Score = 59 WHERE StudentID = 'PB23021112' AND CourseID = '011103'; -- 代数结构
# SELECT GetPassedCredits('PB23021112') as PassedCredits; -- 0
# UPDATE Grade SET Score = 89 WHERE StudentID = 'PB23021112' AND CourseID = '011103'; -- 代数结构
#
# -- GetFailedCourseNum
# SELECT GetFailedCourseNum('PB21111738') as FailedCourseNum; -- 0
# SELECT GetFailedCourseNum('PB22011111') as FailedCourseNum; -- 1
# SELECT GetFailedCourseNum('PB23021112') as FailedCourseNum; -- 2
#
# -- GetFailedCredits
# SELECT GetFailedCredits('PB21111738') as FailedCredits; -- 0
# SELECT GetFailedCredits('PB22011111') as FailedCredits; -- 6.0
# SELECT GetFailedCredits('PB23021112') as FailedCredits; -- 2.5+4.0=6.5
#
# -- GetWeightedAverageScore
# SELECT GetWeightedAverageScore('PB21111738') as WeightedAverageScore; -- (89*3.5+86*4.0+93*3.0)/(3.5+4.0+3.0)=89.14
# SELECT GetWeightedAverageScore('PB22011111') as WeightedAverageScore; -- (55*3.0+88*3.0+88*4.0)/(3.0+3.0+4.0)=76.12
# SELECT GetWeightedAverageScore('PB23021112') as WeightedAverageScore; -- (58*4.0+59*2.5+89*3.0)/(3.0+4.0+2.5)=68.05
# SELECT GetWeightedAverageScore('PB21021112') as WeightedAverageScore; -- NULL