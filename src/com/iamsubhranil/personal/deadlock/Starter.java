/*
    Created By : iamsubhranil
    Date : 28/12/16
    Time : 7:48 PM
    Package : com.iamsubhranil.personal.deadlock
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock;

import com.iamsubhranil.personal.deadlock.processes.ProcessManager;
import com.iamsubhranil.personal.deadlock.ui.WindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Starter extends Application {

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.vsync", "false");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent root = fxmlLoader.load(WindowController.class.getResourceAsStream("window.fxml"));
            Scene aScene = new Scene(root, 600, 600);
            ProcessManager.dummyProcess(10, 3);
            WindowController windowController = fxmlLoader.getController();
            windowController.decorate();
            primaryStage.setScene(aScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
