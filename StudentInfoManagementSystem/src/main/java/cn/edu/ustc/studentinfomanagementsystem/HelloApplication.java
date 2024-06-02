package cn.edu.ustc.studentinfomanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // use SceneManager to store and switch between scenes
        int width = 1280;
        int height = 800;
        SceneManager.getInstance(width, height).setStage(stage);

        stage.setMinWidth(width);
        stage.setMinHeight(height);

        stage.setTitle("学籍管理系统");
        try {
            SceneManager.getInstance().addScene("hello-view", "views/hello-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SceneManager.getInstance().switchScene("hello-view");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}