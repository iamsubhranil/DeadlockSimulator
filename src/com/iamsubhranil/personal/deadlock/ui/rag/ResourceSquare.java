/*
    Created By : iamsubhranil
    Date : 30/12/16
    Time : 9:37 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import com.iamsubhranil.personal.deadlock.resources.Resource;
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

public class ResourceSquare extends Label {

    private final Resource resource;
    private final Rectangle rectangle;
    private final Label resourceLabel;
    private final StackPane stackPane;
    private ObjectProperty<Paint> fill = new SimpleObjectProperty<>(Color.BLACK);

    public ResourceSquare(double width, double height, Resource r) {
        super();
        rectangle = new Rectangle(width, height);
        rectangle.setStroke(Color.valueOf("#1d1d1d"));
        rectangle.setFill(Color.valueOf("#1d1d1d"));
        resource = r;
        resourceLabel = new Label(resource.getResourceName());

        stackPane = new StackPane(rectangle);
        stackPane.setMaxWidth(width);
        drawInstances();
        setGraphic(new VBox(stackPane, resourceLabel));
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
}
