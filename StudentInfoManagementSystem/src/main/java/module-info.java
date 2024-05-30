module cn.edu.ustc.studentinfomanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;


    opens cn.edu.ustc.studentinfomanagementsystem to javafx.fxml;
    exports cn.edu.ustc.studentinfomanagementsystem;
}