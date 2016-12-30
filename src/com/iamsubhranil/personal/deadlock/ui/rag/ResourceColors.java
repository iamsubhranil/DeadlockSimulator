/*
    Created By : iamsubhranil
    Date : 30/12/16
    Time : 10:41 PM
    Package : com.iamsubhranil.personal.deadlock.ui.rag
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui.rag;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class ResourceColors {

    private static final ArrayList<String> colors = new ArrayList<>();

    static {
        colors.addAll(Arrays.asList("#fba71b", "#e00033", "#99b433", "#1e7145", "#7e3878", "#00aba9",
                "#2d89ef", "#2b5797", "#e3a21a", "#da532c", "#ee1111", "#b91d47",
                "#76608a", "#6d8764", "#87794e", "#c3cb71", "#ae5a41", "#edc951",
                "#00a0b0", "#f3b200", "#77b900", "#2572eb", "#ad103c", "#632f00",
                "#b01e00", "#c1004f", "#7200ac", "#4617b4", "#006ac1", "#008287",
                "#199900", "#00c13f", "#ff981d", "#ff2e12", "#ff1d77", "#aa40ff",
                "#1faeff", "#56c5ff", "#00d8cc", "#91d100", "#e1b700", "#ff76bc",
                "#00a3a3", "#fe7c22"));
    }

    public static Color getColorFor(int index) {
        if (index >= colors.size()) {
            index = index - colors.size();
        }
        return Color.valueOf(colors.get(index));
    }

    public static Color getColorForProcess(int index) {
        return getColorFor(index > colors.size() ? index : colors.size() - index);
    }

}
