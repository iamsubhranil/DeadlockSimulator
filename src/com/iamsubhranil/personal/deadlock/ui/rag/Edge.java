/*
    Created By : iamsubhranil
    Date : 1/1/17
    Time : 4:09 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import javafx.scene.paint.Paint;
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
            startXProperty().bind(to.layoutXProperty().add(to.squareSizeProperty().divide(2)));
            startYProperty().bind(to.layoutYProperty().add(to.squareSizeProperty().divide(2)));

            System.out.println("Process " + from.getProcess().getPid() + " has acquired " + to.getResource().getResourceName());

            if (processCircle.getProcess().getResourceMap().getAllocatedInstancesCount(to.getResource()) == to.getResource().getTotalInstances()) {
                setStroke(to.getPaint());
            }

            endXProperty().bind(Pointer.decideCircleEnd(to.layoutXProperty(), from.layoutXProperty(), from.radiusProperty()));
            endYProperty().bind(Pointer.decideCircleEnd(to.layoutYProperty(), from.layoutYProperty(), from.radiusProperty()));
        }

        setOnMouseEntered(mouseEvent -> {
            setUserData(getStroke());
            if (isRequestEdge) {
                setStroke(from.getPaint());
            } else {
                setStroke(to.getPaint());
            }
        });

        setOnMouseExited(m -> setStroke((Paint) getUserData()));
    }

    public Edge(ProcessCircle from, ResourceSquare to) {
        this(from, to, true);
    }

    public Edge(ResourceSquare from, ProcessCircle to) {
        this(to, from, false);
    }

}
