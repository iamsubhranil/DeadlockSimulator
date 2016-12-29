/*
    Created By : iamsubhranil
    Date : 28/12/16
    Time : 7:53 PM
    Package : com.iamsubhranil.personal.deadlock.resources
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.resources;

import com.iamsubhranil.personal.deadlock.processes.Process;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Hashtable;

public class Resource {

    private final String resourceName;
    private final Hashtable<Process, Integer> processMap = new Hashtable<>();
    private final SimpleIntegerProperty totalInstances;
    private final SimpleIntegerProperty allocatedInstances = new SimpleIntegerProperty(0);
    private ResourcePriority resourcePriority;

    public Resource(String res, ResourcePriority resp, int totIns) {
        resourceName = res;
        resourcePriority = resp;
        totalInstances = new SimpleIntegerProperty(totIns);
    }

    public Resource(String res, int totIns) {
        this(res, ResourcePriority.MED, totIns);
    }

    public String getResourceName() {
        return resourceName;
    }

    public ResourcePriority getResourcePriority() {
        return resourcePriority;
    }

    public int getTotalInstances() {
        return totalInstances.get();
    }

    public int getAllocatedInstances() {
        return allocatedInstances.get();
    }

    public int getAvailableInstances() {
        return totalInstances.subtract(allocatedInstances).intValue();
    }

    public synchronized void allocateToProcess(final Process process) throws ResourceException {
        if (totalInstances == allocatedInstances)
            throw new ResourceException("All instances are already allocated!");
        if (process.getResourceMap().getRequiredInstancesCount(this) > 0) {
            if (process.getResourceMap().getAllocatedInstancesCount(this) < process.getResourceMap().getRequiredInstancesCount(this)) {
                if (processMap.contains(process)) {
                    processMap.replace(process, processMap.get(process) + 1);
                } else {
                    processMap.put(process, 1);
                }
                allocatedInstances.set(allocatedInstances.getValue() + 1);
            } else {
                throw new ResourceException("The process has already been allocated maximum number of this resource!");
            }
        } else {
            throw new ResourceException("The process does not require a resource of this kind!");
        }
    }

    public synchronized void relocateFromProcess(Process process) {
        processMap.remove(process);
        allocatedInstances.set(allocatedInstances.getValue() - 1);
    }

}
