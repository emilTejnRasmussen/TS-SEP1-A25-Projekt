package kloeverly.presentation.controllers.common_task;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.CommonTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

import java.util.List;

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
    }

    public void handleUpdate()
    {
    }

    public void handleDelete()
    {
    }

    private void showTable(List<CommonTask> tasks){
        allCommonTasksTable.getItems().clear();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        allCommonTasksTable.getItems().addAll(tasks);
    }
}
