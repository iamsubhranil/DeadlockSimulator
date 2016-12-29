/*
    Created By : iamsubhranil
    Date : 29/12/16
    Time : 3:57 PM
    Package : com.iamsubhranil.personal.deadlock.ui
    Project : DeadlockSimulator
*/
package com.iamsubhranil.personal.deadlock.ui;

import com.iamsubhranil.personal.deadlock.processes.Process;
import com.iamsubhranil.personal.deadlock.processes.ProcessManager;
import com.iamsubhranil.personal.deadlock.resources.Resource;
import com.iamsubhranil.personal.deadlock.resources.ResourceManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class WindowController {

    public TableView<Process> processTable;
    public TableColumn<Process, String> processNameColumn;
    public TableColumn<Process, Long> pidColumn;
    public TableColumn resourceRequirementColumn;
    public TableView<Resource> resourceTable;
    public TableColumn<Resource, String> resourceNameColumn;
    public TableColumn resourceInstancesColumn;
    public TableColumn<Resource, Integer> resourceTotalInstancesColumn;
    public TableColumn<Resource, Integer> resourceAllocatedInstancesColumn;
    public TableColumn<Resource, Integer> resourceFreeInstancesColumn;

    public void decorate() {
        processNameColumn.setCellValueFactory(new PropertyValueFactory<>("processName"));
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));

        ArrayList<Resource> resourceArrayList = ResourceManager.getResources();
        int noOfRes = resourceArrayList.size();
        int count = 0;
        while (count < noOfRes) {
            TableColumn<Process, Integer> resourceIntegerTableColumn = new TableColumn<>(resourceArrayList.get(count).getResourceName());
            int finalNoOfRes = count;
            resourceIntegerTableColumn.setCellValueFactory(processIntegerCellDataFeatures -> new SimpleIntegerProperty(processIntegerCellDataFeatures.getValue().getResourceMap().getRequiredInstancesCount(resourceArrayList.get(finalNoOfRes))).asObject());
            resourceRequirementColumn.getColumns().add(resourceIntegerTableColumn);
            count++;
        }
        processTable.setItems(FXCollections.observableArrayList(ProcessManager.getProcesses()));
        decorateResources();
    }

    private void decorateResources() {
        resourceNameColumn.setCellValueFactory(new PropertyValueFactory<>("resourceName"));
        resourceTotalInstancesColumn.setCellValueFactory(new PropertyValueFactory<>("totalInstances"));
        resourceAllocatedInstancesColumn.setCellValueFactory(new PropertyValueFactory<>("allocatedInstances"));
        resourceFreeInstancesColumn.setCellValueFactory(new PropertyValueFactory<>("availableInstances"));
        resourceTable.setItems(FXCollections.observableArrayList(ResourceManager.getResources()));
    }

}
