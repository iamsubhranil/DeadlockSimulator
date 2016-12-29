/*
    Created By : iamsubhranil
    Date : 29/12/16
    Time : 8:37 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RAGTest extends Application {

    private final AnchorPane anchorPane = new AnchorPane();
    private final double processRad = 50;

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

    private void drawProcess(double x, double y) {
        Circle circle = new Circle(x, y, processRad, Paint.valueOf("#00E32FFF"));
        Label pName = new Label("Process1");
        pName.setLayoutX(x - 30);
        pName.setLayoutY(y - 10);
        anchorPane.getChildren().addAll(circle, pName);

    }

    private void drawResource(double x, double y) {
        Rectangle rectangle = new Rectangle(50, 50);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        Label rName = new Label("Resource1");
        rName.setLayoutY(y + 50);
        rName.setLayoutX(x);
        rectangle.setFill(Paint.valueOf("#00F67543"));
        anchorPane.getChildren().addAll(rectangle, rName);
    }

    private void drawRequestEdge(double x1, double y1, double x2, double y2, double recRadius) {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(0.0, 0.0,
                0.0, 15.0,
                15.0, 0.0);
        double slope = y1 / x2;
        triangle.setRotate(165 + ((slope * 180) / 3.14));
        triangle.setLayoutX(x2 - 7.5);
        triangle.setLayoutY(y2 - 7.5);
        Line line = new Line();
        line.setStartX(x1);
        line.setEndX(x2);
        line.setStartY(y1);
        line.setEndY(y2);
        anchorPane.getChildren().addAll(triangle, line);

    }

    private void draw() {
        double px1 = 100, py1 = px1, px2 = 400, py2 = px1;
        double rx1 = 300, ry1 = rx1, rx2 = 200, ry2 = 300;
        drawResource(rx1, ry1);
        drawResource(rx2, ry2);

        drawRequestEdge(px1, py1, rx1, ry1, 50);
        drawRequestEdge(px1, py1, rx2, ry2, 50);

        drawRequestEdge(px2, py2, rx1, ry1, 50);
        drawRequestEdge(px2, py2, rx2, ry2, 50);

        drawProcess(px1, px1);
        drawProcess(px2, py2);
    }
}
