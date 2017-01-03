/*
    Created By : iamsubhranil
    Date : 28/12/16
    Time : 9:56 PM
    Package : com.iamsubhranil.personal.deadlock.processes
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.processes;

import com.iamsubhranil.personal.deadlock.resources.Resource;
import com.iamsubhranil.personal.deadlock.resources.ResourceException;
import com.iamsubhranil.personal.deadlock.resources.ResourceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ProcessManager {

    private static final ArrayList<Process> processList = new ArrayList<>();
    private static long pid = 1;

    public static Process generateNewProcess(String processName, Map<Resource, Integer> resourceIntegerMap) {
        Process p = new Process(processName, pid, resourceIntegerMap);
        processList.add(p);
        pid++;
        return p;
    }

    public static ArrayList<Process> getProcesses() {
        return processList;
    }

    public static void typicalDeadlock() {
        Resource r1 = ResourceManager.generateNewResource("R1", 1);
        Resource r2 = ResourceManager.generateNewResource("R2", 1);
        HashMap<Resource, Integer> resourceIntegerHashMap = new HashMap<>();
        resourceIntegerHashMap.put(r1, 1);
        resourceIntegerHashMap.put(r2, 1);
        Process p1 = generateNewProcess("P1", resourceIntegerHashMap);
        Process p2 = generateNewProcess("P2", resourceIntegerHashMap);
        try {
            p1.getResourceMap().requestForResource(r1, 1);
            p2.getResourceMap().requestForResource(r2, 1);
        } catch (ResourceException e) {
            e.printStackTrace();
        }

    }

    public static void dummyProcess(int processNum, int resourceNum) {
        ResourceManager.dummyResources(resourceNum);
        int count1 = 0;
        while (count1 < processNum) {
            HashMap<Resource, Integer> resourceIntegerHashMap = new HashMap<>();
            int count = 0;
            while (count < resourceNum) {
                try {
                    resourceIntegerHashMap.put(ResourceManager.getResources().get(count),
                            new Random().nextInt(ResourceManager.getResources().get(count).getTotalInstances()) - 1);
                } catch (IllegalArgumentException iae) {
                    System.out.println(ResourceManager.getResources().get(count).getTotalInstances());
                }
                count++;
            }
            generateNewProcess("Process" + count1, resourceIntegerHashMap);
            count1++;
        }

    }

    public static void main(String[] args) {
        Resource r1 = ResourceManager.generateNewResource("Printer", 4);
        Resource r2 = ResourceManager.generateNewResource("Monitor", 4);
        HashMap<Resource, Integer> p1rMap = new HashMap<>();
        p1rMap.put(r1, 2);
        p1rMap.put(r2, 3);
        Process p1 = generateNewProcess("P1", p1rMap);
        HashMap<Resource, Integer> p2rMap = new HashMap<>();
        p2rMap.put(r1, 4);
        p2rMap.put(r2, 4);
        Process p2 = generateNewProcess("P2", p1rMap);
        ResourceManager.dummyResources(5);
    }

}
