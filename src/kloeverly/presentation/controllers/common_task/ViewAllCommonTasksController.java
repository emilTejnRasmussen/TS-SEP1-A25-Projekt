package kloeverly.presentation.controllers.common_task;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.CommonTask;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

import java.util.List;
import java.util.Optional;

public class ViewAllCommonTasksController implements InitializableController
{
    @FXML
    private TableView<CommonTask> allCommonTasksTable;
    @FXML
    private TableColumn<CommonTask, String> nameCol;
    @FXML
    private TableColumn<CommonTask, Integer> valueCol;
    @FXML
    private TextField searchTxtField;

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        showTable(dataManager.getAllCommonTasks());
    }

    public void handleSearch()
    {
    }

    public void handleClearSearchBar()
    {
    }

    public void handleAdd()
    {
        ViewManager.showView(Views.ADD_COMMON_TASK);
    }

    public void handleViewDetails()
    {
        Task task = allCommonTasksTable.getSelectionModel().getSelectedItem();
        if (task == null) return;

        ViewManager.showView(Views.COMMON_TASK, task.getId() + "");
    }

    public void handleUpdate()
    {
        Task task = allCommonTasksTable.getSelectionModel().getSelectedItem();
        if (task == null) return;

        ViewManager.showView(Views.UPDATE_COMMON_TASK, task.getId() + "");
    }

    public void handleDelete()
    {
        Task selectedTask = allCommonTasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bekræft sletning");
        confirm.setHeaderText("Slet fællesopgave");
        confirm.setContentText("Er du sikker på, at du vil slette: '" + selectedTask.getName() + "'?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            dataManager.deleteTask(selectedTask);
            showTable(dataManager.getAllCommonTasks());
        }

    }

    private void showTable(List<CommonTask> tasks){
        allCommonTasksTable.getItems().clear();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        allCommonTasksTable.getItems().addAll(tasks);
    }
}
