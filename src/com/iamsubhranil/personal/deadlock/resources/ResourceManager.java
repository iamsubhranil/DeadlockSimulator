/*
    Created By : iamsubhranil
    Date : 28/12/16
    Time : 10:01 PM
    Package : com.iamsubhranil.personal.deadlock.resources
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.resources;

import java.util.ArrayList;
import java.util.Random;

public class ResourceManager {

    private static final ArrayList<Resource> resources = new ArrayList<>();

    public static Resource generateNewResource(String name, int totIns) {
        Resource resource = new Resource(name, totIns);
        resources.add(resource);
        return resource;
    }

    public static void dummyResources(int resNum) {
        int count = 0;
        while (count < resNum) {
            generateNewResource("Resource" + count, new Random().nextInt(19) + 1);
            count++;
        }
    }

    public static ArrayList<Resource> getResources() {
        return resources;
    }

}
