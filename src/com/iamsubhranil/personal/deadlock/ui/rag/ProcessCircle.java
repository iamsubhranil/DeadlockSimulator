/*
    Created By : iamsubhranil
    Date : 30/12/16
    Time : 8:50 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import com.iamsubhranil.personal.deadlock.processes.Process;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;


public class ProcessCircle extends Label {

    private final Process process;
    private final Circle circle;
    private final Label pidLabel;
    private final Tooltip info = new Tooltip();
    private final ObjectProperty<Paint> paint;
    private double mouseX;
    private double mouseY;

    public ProcessCircle(Process p, double centerX, double centerY, double radius, Paint fill) {
        super();
        paint = new SimpleObjectProperty<>(fill);
        circle = new Circle(radius);
        circle.fillProperty().bind(paint);
        process = p;
        pidLabel = new Label(process.getPid() + "");
        pidLabel.setFont(Font.font(20));

        circle.setOnMouseEntered(mouseEvent -> {
            info.setText("Process : " + p.getProcessName() + "\nResource requirement : \n" +
                    process.getResourceMap().getRemainingRequirementsInString());
            info.show(circle.getScene().getWindow(), mouseEvent.getScreenX(), mouseEvent.getSceneY());
            //   System.out.println("Tooltip showing at : "+mouseEvent.getScreenX()+", "+mouseEvent.getScreenY());
        });
        //   pidLabel.setOnMouseEntered(getOnMouseEntered());
        circle.setOnMouseExited(mouseEvent -> {
            if (info.isShowing()) {
                info.hide();
            }
        });
        //    pidLabel.setOnMouseExited(getOnMouseExited());
        setGraphic(new StackPane(circle, pidLabel));
        setLayoutX(centerX - radius);
        setLayoutY(centerY - radius);

        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;
            setLayoutX(getLayoutX() + deltaX);
            setLayoutY(getLayoutY() + deltaY);
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

    }

    public Process getProcess() {
        return process;
    }

    public double getRadius() {
        return circle.getRadius();
    }

    public DoubleProperty radiusProperty() {
        return circle.radiusProperty();
    }

    public Paint getPaint() {
        return paint.get();
    }

}
