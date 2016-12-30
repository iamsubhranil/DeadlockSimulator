/*
    Created By : iamsubhranil
    Date : 30/12/16
    Time : 8:50 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import com.iamsubhranil.personal.deadlock.processes.Process;
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

    public ProcessCircle(Process p, double centerX, double centerY, double radius, Paint fill) {
        super();
        circle = new Circle(radius, fill);
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

    }

}
