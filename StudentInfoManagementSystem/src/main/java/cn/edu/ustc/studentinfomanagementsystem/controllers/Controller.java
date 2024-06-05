package cn.edu.ustc.studentinfomanagementsystem.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

public class Controller {
    public void setText(@NotNull TextField tf, String text) {
        tf.setText(text);
    }

    public void setText(@NotNull Label l, String text) {
        l.setText(text);
    }

    public void setImage(@NotNull ImageView iv, Image i) {
        iv.setImage(i);
    }

    public void setImage(@NotNull ImageView iv, String url) {
        setImage(iv, new Image(url));
    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        // wait for the user to respond
        alert.showAndWait();
    }
    public static void showAlert(String title, String message, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        // wait for the user to respond
        alert.showAndWait();
    }
}
