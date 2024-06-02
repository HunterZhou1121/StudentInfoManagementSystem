package cn.edu.ustc.studentinfomanagementsystem.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import cn.edu.ustc.studentinfomanagementsystem.SceneManager;
import cn.edu.ustc.studentinfomanagementsystem.utils.alertUtils;

import java.io.IOException;

public class HelloController {
    @FXML private AnchorPane anchorPane;

    @FXML private Label label;

    @FXML private Button startButton;

    @FXML private Button exitButton;

    @FXML
    private void handleStartButtonAction() {
        try {
            SceneManager.getInstance().addScene("login-view", "views/login-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SceneManager.getInstance().switchScene("login-view");
    }

    @FXML
    private void handleExitButtonAction() {
        // alert: "感谢使用"
        alertUtils.showAlert("感谢使用", "感谢使用本系统！");
        Platform.exit();
    }

}