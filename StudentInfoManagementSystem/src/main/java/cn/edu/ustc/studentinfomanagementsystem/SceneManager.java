package cn.edu.ustc.studentinfomanagementsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;
    private final Map<String, Scene> scenes;

    private final Map<String, FXMLLoader> loaders;

    // use a stack to keep track of the scene history
//    private final Stack<String> sceneStack;

    private final int width;
    private final int height;

    private SceneManager(int width, int height) {
        this.width = width;
        this.height = height;
        scenes = new HashMap<>();
        loaders = new HashMap<>();
//        sceneStack = new Stack<>();
    }

    public static SceneManager getInstance(int width, int height) {
        if (instance == null) {
            instance = new SceneManager(width, height);
        }
        return instance;
    }

    public static SceneManager getInstance() {
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

//    public void switchScene(String fxml) {
//        if (!scenes.containsKey(fxml)) {    // the scene has not been loaded
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//                Parent root = loader.load();
//                Scene scene = new Scene(root, width, height);
//                sceneStack.push(fxml);
//                scenes.put(fxml, scene);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        stage.setScene(scenes.get(fxml));
//    }

    public void addScene(String name, String fxml) throws IOException {
        if (!scenes.containsKey(name)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            scenes.put(name, scene);
            loaders.put(name, loader);
//            sceneStack.push(name);
        }
    }

    public void switchScene(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            stage.setScene(scene);
        } else {
            System.out.println("Scene not found: " + name);
        }
    }

    public void clearScene(String name) {
        if (scenes.containsKey(name)) {
            scenes.remove(name);
            loaders.remove(name);
            // remove the scene from the stack
//            sceneStack.remove(name);
        }
    }

//    public void back() {
//        if (sceneStack.size() > 1) {
//            sceneStack.pop();
//            String fxml = sceneStack.peek();
//            stage.setScene(scenes.get(fxml));
//        }
//    }
    public FXMLLoader getLoader(String name) {
        return loaders.get(name);
    }
}
