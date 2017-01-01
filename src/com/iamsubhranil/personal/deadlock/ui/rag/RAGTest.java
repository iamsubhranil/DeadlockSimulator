/*
    Created By : iamsubhranil
    Date : 29/12/16
    Time : 8:37 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import com.iamsubhranil.personal.deadlock.processes.ProcessManager;
import com.iamsubhranil.personal.deadlock.resources.ResourceManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RAGTest extends Application {

    private final AnchorPane anchorPane = new AnchorPane();
    private final double processRad = 30;
    private int abc = 100;

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene s = new Scene(anchorPane, 900, 600);
        anchorPane.setPadding(new Insets(10, 10, 10, 10));
        primaryStage.setScene(s);
        primaryStage.show();
        draw();
    }

    private ProcessCircle processAt(double x, double y, int count) {
        return new ProcessCircle(ProcessManager.getProcesses().get(count), x, y, processRad, ResourceColors.getColorForProcess(count));
    }

    private ResourceSquare resourceAt(double x, double y, int count) {
        ResourceSquare resourceSquare = new ResourceSquare(50,
                ResourceManager.getResources().get(count));
        resourceSquare.setLayoutX(x);
        resourceSquare.setLayoutY(y);
        resourceSquare.setFill(ResourceColors.getColorFor(count));
        return resourceSquare;
    }

    private void drawEdgeBetween(ProcessCircle from, ResourceSquare to) {
        Edge edge = new Edge(from, to);
        Triangle triangle = new Triangle(edge);
        anchorPane.getChildren().addAll(edge, triangle);
    }

    private void draw() {

        ProcessManager.dummyProcess(3, 3);
        double px1 = 100, px2 = 400;
        double rx1 = 300, rx2 = 200, ry2 = 300;
        ResourceSquare r1 = resourceAt(rx1, rx1, 0);
        ResourceSquare r2 = resourceAt(rx2, ry2, 1);
        ResourceSquare r3 = resourceAt(px1, ry2, 2);

        ProcessCircle p1 = processAt(px1, px1, 0);
        ProcessCircle p2 = processAt(px2, px1, 1);
        ProcessCircle p3 = processAt(rx1 - 50, 200, 2);

        anchorPane.getChildren().addAll(r1, r2, r3);

        drawEdgeBetween(p1, r1);
        drawEdgeBetween(p1, r3);

        drawEdgeBetween(p2, r2);
        drawEdgeBetween(p2, r1);

        drawEdgeBetween(p3, r2);
        drawEdgeBetween(p3, r3);


        anchorPane.getChildren().addAll(p1, p2, p3);

    }
}
