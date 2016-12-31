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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
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
        Scene s = new Scene(anchorPane, 500, 500);
        anchorPane.setPadding(new Insets(10, 10, 10, 10));
        primaryStage.setScene(s);
        primaryStage.show();
        draw();
    }

    private void drawProcess(double x, double y, int count) {
        ProcessCircle circle = new ProcessCircle(ProcessManager.getProcesses().get(count), x, y, processRad, ResourceColors.getColorForProcess(count));
        anchorPane.getChildren().addAll(circle);

    }

    private void drawResource(double x, double y, int count) {
        ResourceSquare resourceSquare = new ResourceSquare(50, 50,
                ResourceManager.getResources().get(count));
        resourceSquare.setLayoutX(x);
        resourceSquare.setLayoutY(y);
        resourceSquare.setFill(ResourceColors.getColorFor(count));
        anchorPane.getChildren().addAll(resourceSquare);
    }

    private double[] decidePoints(double x1, double y1, double x2, double y2, double recRadius) {
        double[] ret = new double[2];

        double centerX = x2 + recRadius / 2;
        double centerY = y2 + recRadius / 2;
        double retX = 0;
        double retY = 0;
        System.out.println("x1 : " + x1 + "y1 : " + y1 + "\tx2 : " + x2 + " y2 : " + y2 + "\tcenterX : " + centerX + " centerY : " + centerY);
        if (x1 == x2) {
            retX = centerX;
        } else if (centerX > x1) {
            retX = x2;
        } else if (centerX < x1) {
            retX = centerX + recRadius / 2;
        }

        if (y1 == y2) {
            retY = centerY;
        } else if (centerY > y1) {
            retY = y2;
        } else if (centerY < y1) {
            retY = centerY + recRadius / 2;
        }

        ret[0] = retX;
        ret[1] = retY;

        return ret;
    }

    private double decideRotate(double x1, double y1, double x2, double y2) {
        double slope = Math.toDegrees(y1 / x2);
        boolean xBig = x1 > x2;
        boolean yBig = y1 > y2;
        if (!xBig && !yBig) {
            slope = slope + 135;
        } else if (xBig && !yBig) {
            slope = slope - 135;
        } else if (xBig && yBig) {
            slope = slope + 45;
        } else {
            slope = slope + 45;
        }
        return slope;
    }

    private void drawRequestEdge(double x1, double y1, double x2, double y2, double recRadius) {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(0.0, 0.0,
                0.0, 15.0,
                15.0, 0.0);
        triangle.setRotate(decideRotate(x1, y1, x2, y2) + 45);
        double x = decidePoints(x1, y1, x2, y2, recRadius)[0];
        double y = decidePoints(x1, y1, x2, y2, recRadius)[1];
        triangle.setLayoutX(x - 7.5);
        triangle.setLayoutY(y - 15);
        triangle.setFill(Color.WHITESMOKE);
        triangle.setStroke(Color.BLACK);
        Line line = new Line();
        line.setStartX(x1);
        line.setEndX(x);
        line.setStartY(y1);
        line.setEndY(y);
        anchorPane.getChildren().addAll(triangle, line);

    }

    private void draw() {

        ProcessManager.dummyProcess(3, 3);
        double px1 = 100, px2 = 400;
        double rx1 = 300, rx2 = 200, ry2 = 300;
        drawResource(rx1, rx1, 0);
        drawResource(rx2, ry2, 1);
        drawResource(100, ry2, 2);

        drawRequestEdge(px1, px1, rx1, rx1, 50);
        drawRequestEdge(px1, px1, rx2, ry2, 50);

        drawRequestEdge(px2, px1, rx1, rx1, 50);
        drawRequestEdge(px2, px1, rx2, ry2, 50);

        drawRequestEdge(px2, px1, 100, ry2, 50);

        drawRequestEdge(300, 250, rx1, rx1, 50);
        drawRequestEdge(300, 250, 100, ry2, 50);


        drawProcess(px1, px1, 0);
        drawProcess(px2, px1, 1);
        drawProcess(300, 250, 2);
    }
}
