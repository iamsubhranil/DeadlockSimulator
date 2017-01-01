/*
    Created By : iamsubhranil
    Date : 1/1/17
    Time : 4:37 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Pointer {

    public static DoubleProperty decideEnd(DoubleExpression from, DoubleExpression to, DoubleProperty recRad) {
        DoubleProperty ret = new SimpleDoubleProperty(0);

        SimpleDoubleProperty centerX = new SimpleDoubleProperty(to.get() + (recRad.get() / 2));
        centerX.bind(to.add(recRad.divide(2)));
        ret.bind(Bindings.when(Bindings.when(from.lessThan(to)).then(to.subtract(from)).otherwise(from.subtract(to))
                .lessThanOrEqualTo(recRad.add(15))).then(centerX)
                .otherwise(Bindings.when(centerX.greaterThan(from)).then(to)
                        .otherwise(centerX.add(recRad.divide(2)))));
        return ret;
    }

    public static double[] decidePoints(double x1, double y1, double x2, double y2, double recRadius) {
        double[] ret = new double[2];

        double centerX = x2 + recRadius / 2;
        double centerY = y2 + recRadius / 2;
        double retX = 0;
        double retY = 0;
        System.out.println("x1 : " + x1 + " y1 : " + y1 + "\tx2 : " + x2 + " y2 : " + y2 + "\tcenterX : " + centerX + " centerY : " + centerY);
        if (Math.abs(x1 - x2) <= (recRadius + 15)) {
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
}
