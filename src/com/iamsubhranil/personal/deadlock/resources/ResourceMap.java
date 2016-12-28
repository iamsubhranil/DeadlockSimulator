/*
    Created By : iamsubhranil
    Date : 28/12/16
    Time : 8:26 PM
    Package : com.iamsubhranil.personal.deadlock.resources
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.resources;

import com.iamsubhranil.personal.deadlock.processes.Process;

import java.util.ArrayList;
import java.util.Objects;

public class ResourceMap {

    private final ArrayList<Resource> resources;
    private final ArrayList<Integer> maximum;
    private final ArrayList<Integer> allocated;
    private final Process ofProcess;

    public ResourceMap(Process p) {

        resources = new ArrayList<>();
        maximum = new ArrayList<>();
        allocated = new ArrayList<>();
        ofProcess = p;

    }

    public void addResource(Resource r, Integer requiredInstances) throws ResourceException {
        final int[] pos = {-1};
        resources.forEach(res -> {
            if (Objects.equals(res.getResourceName(), r.getResourceName())) {
                pos[0] = 0;
            }
        });
        if (pos[0] == 0) {
            throw new ResourceException("Resource is already in list!");
        }
        resources.add(r);
        r.allocateToProcess(ofProcess);
        maximum.add(requiredInstances);
        allocated.add(0);
    }

    public Integer getRequiredInstancesCount(Resource r) {
        if (resources.contains(r)) {
            return maximum.get(resources.indexOf(r));
        }
        return -1;
    }

    public Integer getAllocatedInstancesCount(Resource r) {
        if (resources.contains(r)) {
            return allocated.get(resources.indexOf(r));
        }
        return -1;
    }

    public Integer getReminderInstancesCount(Resource r) {
        if (resources.contains(r)) {
            return maximum.get(resources.indexOf(r)) - allocated.get(resources.indexOf(r));
        }
        return -1;
    }

    public void releaseResource(Resource r) {
        if (resources.contains(r)) {
            maximum.remove(resources.indexOf(r));
            allocated.remove(resources.indexOf(r));
            resources.remove(r);
            r.relocateFromProcess(ofProcess);
        }
    }


}
