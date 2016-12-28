/*
    Created By : iamsubhranil
    Date : 28/12/16
    Time : 8:04 PM
    Package : com.iamsubhranil.personal.deadlock.processes
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.processes;

import com.iamsubhranil.personal.deadlock.resources.Resource;
import com.iamsubhranil.personal.deadlock.resources.ResourceException;
import com.iamsubhranil.personal.deadlock.resources.ResourceMap;

import java.util.Map;

public class Process {

    private final String processName;
    private final long pid;
    private final ResourceMap resourceMap;

    public Process(String name, long pid1) {
        processName = name;
        pid = pid1;
        resourceMap = new ResourceMap(this);
    }

    public Process(String name, long pid1, Map<Resource, Integer> resourceDetails) {
        this(name, pid1);
        resourceDetails.forEach((res, in) -> {
            try {
                resourceMap.addResource(res, in);
            } catch (ResourceException e) {
            }
        });
    }

    public ResourceMap getResourceMap() {
        return resourceMap;
    }

    public String getProcessName() {
        return processName;
    }

    public long getPID() {
        return pid;
    }

}
