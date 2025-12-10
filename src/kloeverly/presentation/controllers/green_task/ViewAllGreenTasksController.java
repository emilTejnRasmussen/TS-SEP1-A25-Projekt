package kloeverly.presentation.controllers.green_task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewAllGreenTasksController implements InitializableController {

    private DataManager dataManager;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<GreenTask> greenTaskTable;

    @FXML
    private TableColumn<GreenTask, String> nameColumn;

    @FXML
    private TableColumn<GreenTask, String> descriptionColumn;

    @FXML
    private TableColumn<GreenTask, Number> pointsColumn; // viser value

    private final ObservableList<GreenTask> allGreenTasks =
            FXCollections.observableArrayList();

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;
        configureColumns();
        loadGreenTasks();
    }

    private void configureColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        // VIGTIGT: property hedder value i Task
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    private void loadGreenTasks() {
        allGreenTasks.setAll(dataManager.getAllGreenTasks());
        greenTaskTable.setItems(allGreenTasks);
    }

    @FXML
    private void handleAddGreenTask() {
        ViewManager.showView(Views.ADD_GREEN_TASK);
    }

    @FXML
    private void handleViewGreenTask() {
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(
                Views.VIEW_SINGLE_GREEN_TASK,
                String.valueOf(selected.getId())
        );
    }

    @FXML
    private void handleUpdateGreenTask() {
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(
                Views.UPDATE_GREEN_TASK,
                String.valueOf(selected.getId())
        );
    }

    @FXML
    private void handleDeleteGreenTask() {
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        dataManager.deleteTask(selected);
        loadGreenTasks();
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText();
        if (query == null) query = "";
        query = query.trim().toLowerCase();

        if (query.isEmpty()) {
            greenTaskTable.setItems(allGreenTasks);
            return;
        }

        ObservableList<GreenTask> filtered = FXCollections.observableArrayList();
        for (GreenTask task : allGreenTasks) {
            if (task.getName().toLowerCase().contains(query)) {
                filtered.add(task);
            }
        }
        greenTaskTable.setItems(filtered);
    }

    @FXML
    private void handleClearSearch() {
        searchField.clear();
        greenTaskTable.setItems(allGreenTasks);
    }
}
