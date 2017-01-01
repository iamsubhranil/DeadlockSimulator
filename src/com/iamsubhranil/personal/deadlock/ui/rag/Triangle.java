/*
    Created By : iamsubhranil
    Date : 1/1/17
    Time : 7:43 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.shape.Polygon;


public class Triangle extends Polygon {

    public Triangle(Edge edge) {
        super();
        setSmooth(true);
        rotateProperty().bind(slopeProperty(edge.startXProperty(), edge.startYProperty(), edge.endXProperty(), edge.endYProperty()));
        setFill(javafx.scene.paint.Color.WHITESMOKE);
        setStroke(javafx.scene.paint.Color.BLACK);
        setStrokeWidth(2);
        strokeProperty().bind(edge.strokeProperty());
        layoutXProperty().bind(edge.endXProperty().subtract(7.5));
        layoutYProperty().bind(edge.endYProperty().subtract(7.5));
        setManaged(false);
        getPoints().addAll(7.5, 0.0,
                0.0, 15.0,
                15.0, 15.0);
    }

    private double decideSlope(double x1, double y1, double x2, double y2) {
        double slope = Math.toDegrees(Math.atan((y2 - y1) / (x2 - x1)));
        if (x1 < x2) {
            return slope + 90;
        }
        return slope - 90;
    }

    private DoubleProperty slopeProperty(DoubleExpression x1, DoubleExpression y1, DoubleExpression x2, DoubleExpression y2) {
        DoubleProperty slope = new SimpleDoubleProperty(decideSlope(x1.getValue(), y1.getValue(), x2.getValue(), y2.getValue()));
        ChangeListener<Number> doubleChangeListener = (observableValue, aDouble, t1) -> slope.set(decideSlope(x1.getValue(), y1.getValue(), x2.getValue(), y2.getValue()));
        x1.addListener(doubleChangeListener);
        x2.addListener(doubleChangeListener);
        y1.addListener(doubleChangeListener);
        y2.addListener(doubleChangeListener);
        return slope;
    }

}
