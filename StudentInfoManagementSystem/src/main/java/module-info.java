module cn.edu.ustc.studentinfomanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpmime;
    requires org.json;
    requires org.jetbrains.annotations;


    opens cn.edu.ustc.studentinfomanagementsystem to javafx.fxml;
    exports cn.edu.ustc.studentinfomanagementsystem;
}