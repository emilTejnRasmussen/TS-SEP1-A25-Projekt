package kloeverly.presentation.controllers.common_task;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.CommonTask;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsFlashMessage;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;
import kloeverly.utility.UtilityMethods;

import java.util.List;
import java.util.Optional;

public class ViewAllCommonTasksController implements InitializableController, AcceptsFlashMessage
{
    @FXML
    private Button detailsBtn;
    @FXML
    private Button registerBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableView<CommonTask> allCommonTasksTable;
    @FXML
    private TableColumn<CommonTask, String> nameCol;
    @FXML
    private TableColumn<CommonTask, Integer> valueCol;
    @FXML
    private TableColumn<CommonTask, Integer> amountCol;
    @FXML
    private TextField searchTxtField;

    private DataManager dataManager;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        showTable(dataManager.getAllCommonTasks());
        UtilityMethods.buttonListener(allCommonTasksTable, detailsBtn, registerBtn, deleteBtn);
    }

    public void handleSearch()
    {
        String search = searchTxtField.getText().toLowerCase();
        List<CommonTask> searchedTasks = dataManager.getAllCommonTasks().stream()
                .filter(c -> c.getName().toLowerCase().contains(search))
                .toList();

        showTable(searchedTasks);
    }

    public void handleClearSearchBar()
    {
        searchTxtField.setText("");
        showTable(dataManager.getAllCommonTasks());
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

    public void handleDelete()
    {
        Task selectedTask = allCommonTasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bekræft sletning");
        confirm.setHeaderText("Slet fællesopgave");
        confirm.setContentText("Er du sikker på, at du vil slette: '" + selectedTask.getName() + "'?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            dataManager.deleteTask(selectedTask);
            showTable(dataManager.getAllCommonTasks());
            ViewManager.updateExternalView();
            setFlashMessage(selectedTask.formatTaskDeleted());
        }

    }

    private void showTable(List<CommonTask> tasks)
    {
        allCommonTasksTable.getItems().clear();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        allCommonTasksTable.getItems().addAll(tasks);
    }

    public void handleRegister()
    {
        CommonTask task = allCommonTasksTable.getSelectionModel().getSelectedItem();
        if (task == null) return;

        ViewManager.showView(Views.REGISTER_COMMON_TASK, task.getId() + "");
    }

    @Override
    public void setFlashMessage(String message)
    {
        UtilityMethods.showFlashMessage(message);
    }
}
