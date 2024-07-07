package com.example.client_hethongxemphim.models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class ScreenManager {
    private static final ScreenManager instance = new ScreenManager();
    private final Stack<Scene> sceneStack = new Stack<>();
    private final Stage primaryStage;

    private ScreenManager() {
        primaryStage = new Stage();
    }

    public static ScreenManager getInstance() {
        return instance;
    }

    public void switchScreen(String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Lưu scene hiện tại vào stack
            sceneStack.push(primaryStage.getScene());

            // Đặt scene mới cho primary stage
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack() {
        if (!sceneStack.isEmpty()) {
            Scene previousScene = sceneStack.pop();
            primaryStage.setScene(previousScene);
            primaryStage.show();
        }
    }
}
