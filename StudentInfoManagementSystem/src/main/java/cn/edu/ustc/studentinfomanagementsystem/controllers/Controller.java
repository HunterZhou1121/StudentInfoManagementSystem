package cn.edu.ustc.studentinfomanagementsystem.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class Controller {
    public void setText(@NotNull TextField tf, String text) {
        tf.setText(text);
    }

    public void setText(@NotNull Label l, String text) {
        l.setText(text);
    }

    public void setText(@NotNull ComboBox<String> cb, String text) {
        if (text == null) {
            cb.setValue(null);
            return;
        }
        // if text is contained in the items of the ComboBox, select it
        if (cb.getItems().contains(text)) {
            cb.getSelectionModel().select(text);
        }
    }

    public void setImage(@NotNull ImageView iv, String url) {
        if (url == null) {
            iv.setImage(null);
            return;
        }
        new Thread(() -> {
            try {
                Image image = new Image(url);
                Platform.runLater(() -> iv.setImage(image));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> iv.setImage(null));
            }
        }
        ).start();
    }

    public void setComboBox(@NotNull ComboBox<String> cb, List<String> list) {
        cb.setItems(FXCollections.observableArrayList(list));
    }

    public void clearText(@NotNull TextField tf) {
        tf.clear();
    }

    public void clearText(@NotNull Label l) {
        l.setText("");
    }

    public void clearText(@NotNull ComboBox<String> cb) {
        cb.setValue(null);
    }

    public void clearImage(@NotNull ImageView iv) {
        iv.setImage(null);
    }

    public void clearDate(@NotNull DatePicker dp) {
        dp.setValue(null);
    }

    public void clear(@NotNull TextField tf) {
        clearText(tf);
    }

    public void clear(@NotNull Label l) {
        clearText(l);
    }

    public void clear(@NotNull ComboBox<String> cb) {
        clearText(cb);
    }

    public void clear(@NotNull ImageView iv) {
        clearImage(iv);
    }

    public void clear(@NotNull DatePicker dp) {
        clearDate(dp);
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

    public static Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType buttonTypeYes = new ButtonType("是");
        ButtonType buttonTypeNo = new ButtonType("否");
        alert.getButtonTypes().setAll(buttonTypeNo, buttonTypeYes);

        return alert.showAndWait();
    }
}
