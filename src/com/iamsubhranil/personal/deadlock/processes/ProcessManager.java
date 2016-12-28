/*
    Created By : iamsubhranil
    Date : 28/12/16
    Time : 9:56 PM
    Package : com.iamsubhranil.personal.deadlock.processes
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.processes;

import com.iamsubhranil.personal.deadlock.resources.Resource;

import java.util.ArrayList;
import java.util.Map;

public class ProcessManager {

    private static final ArrayList<Process> processList = new ArrayList<>();
    private static long pid = 1;

    public static Process generateNewProcess(String processName, Map<Resource, Integer> resourceIntegerMap) {
        Process p = new Process(processName, pid, resourceIntegerMap);
        processList.add(p);
        pid++;
        return p;
    }

}
