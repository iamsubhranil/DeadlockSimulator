/*
    Created By : iamsubhranil
    Date : 29/12/16
    Time : 8:37 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import com.iamsubhranil.personal.deadlock.processes.Process;
import com.iamsubhranil.personal.deadlock.processes.ProcessManager;
import com.iamsubhranil.personal.deadlock.resources.ResourceException;
import com.iamsubhranil.personal.deadlock.resources.ResourceManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

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
        //   draw();
        manual();
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

    private void drawRequestEdge(ProcessCircle from, ResourceSquare to) {
        Edge edge = new Edge(from, to);
        Triangle triangle = new Triangle(edge);
        anchorPane.getChildren().addAll(edge, triangle);
    }

    private void drawAllocationEdge(ResourceSquare from, ProcessCircle to) {
        Edge edge = new Edge(from, to);
        Triangle triangle = new Triangle(edge);
        anchorPane.getChildren().addAll(edge, triangle);
    }

    private void manual() {
        ProcessManager.dummyProcess(2, 2);
        Process p1 = ProcessManager.getProcesses().get(0);
        p1.getResourceMap().getRequiredResources().forEach(resource -> {
            try {
                p1.getResourceMap().requestForResource(resource, 1);
            } catch (ResourceException e) {
                e.printStackTrace();
            }
        });
        drawAll();
    }

    private void drawAll() {
        //   ProcessManager.dummyProcess(5, 5);
        int num = ResourceManager.getResources().size();
        double availableWidth = anchorPane.getScene().getWidth() - 100;
        double gap = 90;
        double x = 100;
        double y = 100;
        int count = 0;
        double endX = 0;
        ArrayList<ResourceSquare> resourceSquares = new ArrayList<>();
        while (count < num) {
            ResourceSquare resourceSquare = resourceAt(x, y, count);
            x = x + gap;
            if (availableWidth < x) {
                endX = x;
                x = 100;
                y += gap;
            }

            resourceSquares.add(resourceSquare);
            count++;
        }
        double endY = y;
        x = 50;
        y = 50;
        num = ProcessManager.getProcesses().size();
        gap = 100;
        count = 0;
        availableWidth = anchorPane.getScene().getWidth() - 20;
        ArrayList<ProcessCircle> processCircles = new ArrayList<>();
        while (count < num) {
            ProcessCircle processCircle = processAt(x, y, count);
            x = x + gap;
            if (y >= 100 && y <= endY) {
                if (x > 100 && x < endX) {
                    x = endX + 30;
                }
            }
            if (availableWidth < x) {
                x = 50;
                y += gap;
            }
            processCircles.add(processCircle);
            count++;
        }
        final int[] c = {0};

        ProcessManager.getProcesses().forEach(process -> {
            process.getResourceMap().getRequiredResources().forEach(resource -> {
                if (process.getResourceMap().getAllocatedInstancesCount(resource) > 0) {
                    drawAllocationEdge(resourceSquares.get(ResourceManager.getResources().indexOf(resource)),
                            processCircles.get(c[0]));
                }
            });
            c[0]++;
        });

        anchorPane.getChildren().addAll(resourceSquares);
        c[0] = 0;

        ProcessManager.getProcesses().forEach(process -> {
            process.getResourceMap().getRequiredResources().forEach(resource -> drawRequestEdge(
                    processCircles.get(c[0]), resourceSquares.get(
                            ResourceManager.getResources().indexOf(resource))));
            c[0]++;
        });

        anchorPane.getChildren().addAll(processCircles);
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

        drawAllocationEdge(r1, p3);

        anchorPane.getChildren().addAll(r1, r2, r3);

        drawRequestEdge(p1, r1);
        drawRequestEdge(p1, r3);

        drawRequestEdge(p2, r2);
        drawRequestEdge(p2, r1);

        drawRequestEdge(p3, r2);
        drawRequestEdge(p3, r3);

        anchorPane.getChildren().addAll(p1, p2, p3);

    }
}
