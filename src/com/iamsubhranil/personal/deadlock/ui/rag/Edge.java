/*
    Created By : iamsubhranil
    Date : 1/1/17
    Time : 4:09 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge extends Line {

    private final ProcessCircle processCircle;
    private final ResourceSquare resourceSquare;
    private final boolean isRequestEdge;

    private Edge(ProcessCircle from, ResourceSquare to, boolean mode) {
        super();
        processCircle = from;
        resourceSquare = to;
        isRequestEdge = mode;

        setSmooth(true);
        setStrokeWidth(2);

        if (isRequestEdge) {
            startXProperty().bind(from.layoutXProperty().add(from.radiusProperty()));
            startYProperty().bind(from.layoutYProperty().add(from.radiusProperty()));

            System.out.println("Process " + from.getProcess().getPid() + " is requesting " + to.getResource().getResourceName());

            endXProperty().bind(Pointer.decideEnd(from.layoutXProperty(), to.layoutXProperty(), to.squareSizeProperty()));
            endYProperty().bind(Pointer.decideEnd(from.layoutYProperty(), to.layoutYProperty(), to.squareSizeProperty()));
        } else {
            setStartX(to.getLayoutX() + to.getSquareSize());
            setStartY(to.getLayoutY() + to.getSquareSize());

            System.out.println("Process " + from.getProcess().getPid() + " has acquired " + to.getResource().getResourceName());

            endXProperty().bind(Pointer.decideEnd(to.layoutXProperty(), from.layoutXProperty(), from.radiusProperty()));
            endYProperty().bind(Pointer.decideEnd(to.layoutYProperty(), from.layoutYProperty(), from.radiusProperty()));

        }

        setOnMouseEntered(mouseEvent -> {
            if (isRequestEdge) {
                setStroke(from.getPaint());
            } else {
                setStroke(to.getPaint());
            }
        });

        setOnMouseExited(m -> {
            setStroke(Color.BLACK);
        });
    }

    public Edge(ProcessCircle from, ResourceSquare to) {
        this(from, to, true);
    }

    public Edge(ResourceSquare from, ProcessCircle to) {
        this(to, from, false);
    }

}
