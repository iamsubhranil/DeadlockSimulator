/*
    Created By : iamsubhranil
    Date : 30/12/16
    Time : 9:37 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import com.iamsubhranil.personal.deadlock.resources.Resource;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class ResourceSquare extends Label {

    private final Resource resource;
    private final Rectangle rectangle;
    private final Label resourceLabel;
    private final StackPane stackPane;
    private final ArrayList<Circle> instancesList = new ArrayList<>();
    private double mouseX;
    private double mouseY;
    private ObjectProperty<Paint> fill = new SimpleObjectProperty<>(Color.BLACK);
    private int count = 0;

    public ResourceSquare(double size, Resource r) {
        super();
        rectangle = new Rectangle(size, size);
        rectangle.setStroke(Color.valueOf("#1d1d1d"));
        rectangle.setFill(Color.valueOf("#1d1d1d"));
        resource = r;
        resourceLabel = new Label(resource.getResourceName());
        resourceLabel.setFont(Font.font(9));

        stackPane = new StackPane(rectangle);
        stackPane.setMaxWidth(size);
        drawInstances();
        setGraphic(new VBox(stackPane, resourceLabel));

        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;
            relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

    }

    public DoubleProperty squareSizeProperty() {
        return rectangle.heightProperty();
    }

    public double getSquareSize() {
        return rectangle.getHeight();
    }

    public Resource getResource() {
        return resource;
    }

    private void drawInstances() {
        int instances = resource.getTotalInstances();
        double width = rectangle.getWidth() - 15;
        double completedWidth = 0;
        VBox layer = new VBox();
        layer.setPadding(new Insets(10, 10, 10, 10));
        HBox hBox = new HBox();
        hBox.setSpacing(3);
        layer.setSpacing(3);
        layer.getChildren().addAll(hBox);
        while (instances > 0) {
            Circle circle = new Circle(2);
            circle.fillProperty().bind(fill);
            instancesList.add(circle);
            hBox.getChildren().add(circle);
            completedWidth = completedWidth + 7;
            if (completedWidth >= (width - 5)) {
                hBox = new HBox();
                hBox.setSpacing(3);
                layer.getChildren().addAll(hBox);
                completedWidth = 0;
            }
            instances--;
        }
        stackPane.getChildren().addAll(layer);
    }

    public void setFill(Paint value) {
        fill.setValue(value);
    }

    public Circle getNextInstanceCircle() {
        return instancesList.get(count++);
    }

    public Paint getPaint() {
        return fill.get();
    }
}
