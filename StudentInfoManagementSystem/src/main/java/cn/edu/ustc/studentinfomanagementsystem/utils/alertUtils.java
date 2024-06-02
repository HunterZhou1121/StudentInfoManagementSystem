package cn.edu.ustc.studentinfomanagementsystem.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class alertUtils {
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        // wait for the user to respond
        alert.showAndWait();
    }
}
