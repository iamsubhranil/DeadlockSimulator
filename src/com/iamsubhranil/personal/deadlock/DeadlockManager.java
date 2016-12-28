/*
    Created By : iamsubhranil
    Date : 28/12/16
    Time : 9:46 PM
    Package : com.iamsubhranil.personal.deadlock
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock;

public class DeadlockManager {

    private static final Object locker = new Object();

    public static Object getLocker() {
        return locker;
    }

}
