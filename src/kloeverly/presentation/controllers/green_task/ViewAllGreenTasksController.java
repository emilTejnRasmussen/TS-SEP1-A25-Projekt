package kloeverly.presentation.controllers.green_task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;
import kloeverly.utility.UtilityMethods;

public class ViewAllGreenTasksController implements InitializableController
{
    @FXML
    private Button deleteBtn;
    @FXML
    private Button detailsBtn;
    @FXML
    private Button registerBtn;

    @FXML
    private TextField searchTxtField;

    @FXML
    private TableView<GreenTask> greenTaskTable;

    @FXML
    private TableColumn<GreenTask, String> nameColumn;

    @FXML
    private TableColumn<GreenTask, String> descriptionColumn;

    @FXML
    private TableColumn<GreenTask, Number> pointsColumn;

    private DataManager dataManager;
    private final ObservableList<GreenTask> allGreenTasks = FXCollections.observableArrayList();

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;

        configureColumns();
        loadGreenTasks();
        UtilityMethods.buttonListener(greenTaskTable, detailsBtn, registerBtn, deleteBtn);
    }

    private void configureColumns()
    {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("value")); // value = point
    }

    private void loadGreenTasks()
    {
        allGreenTasks.setAll(dataManager.getAllGreenTasks());
        greenTaskTable.setItems(allGreenTasks);
    }

    // --- Template handlers ---

    @FXML
    private void handleAdd()
    {
        ViewManager.showView(Views.ADD_GREEN_TASK);
    }

    @FXML
    private void handleViewDetails()
    {

        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.VIEW_SINGLE_GREEN_TASK, String.valueOf(selected.getId()));
    }

    @FXML
    private void handleUpdate()
    {

        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.UPDATE_GREEN_TASK, String.valueOf(selected.getId()));
    }

    @FXML
    private void handleDelete()
    {

        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        dataManager.deleteTask(selected);
        ViewManager.updateExternalView();
        loadGreenTasks();
    }

    @FXML
    private void handleSearch()
    {
        String query = searchTxtField.getText();
        if (query == null) query = "";
        query = query.trim().toLowerCase();

        if (query.isEmpty())
        {
            greenTaskTable.setItems(allGreenTasks);
            return;
        }

        ObservableList<GreenTask> filtered = FXCollections.observableArrayList();
        for (GreenTask task : allGreenTasks)
        {
            if (task.getName() != null && task.getName().toLowerCase().contains(query))
            {
                filtered.add(task);
            }
        }

        greenTaskTable.setItems(filtered);
    }

    @FXML
    private void handleClearSearchBar()
    {
        searchTxtField.clear();
        greenTaskTable.setItems(allGreenTasks);
    }

    public void handleRegister()
    {
        ViewManager.showView(Views.REGISTER_GREEN_TASK);
    }
}

