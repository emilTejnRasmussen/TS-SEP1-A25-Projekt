package kloeverly.presentation.controllers.green_task;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.GreenTask;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsFlashMessage;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;
import kloeverly.utility.UtilityMethods;

public class ViewAllGreenTasksController implements InitializableController, AcceptsFlashMessage
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

    @FXML public TableView<GreenTask> greenTaskTable;
    @FXML public TableColumn<GreenTask, String> nameColumn;
    @FXML public TableColumn<GreenTask, String> descriptionColumn;
    @FXML public TableColumn<GreenTask, Number> pointsColumn;

    @FXML public Button addButton;
    @FXML public Button viewDetailsButton;
    @FXML public Button registerButton;
    @FXML public Button updateButton;
    @FXML public Button deleteButton;

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
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        loadGreenTasks();

        // Tilføj er altid aktiv
        setButtonState(addButton, true);

        // De andre kræver selection
        setSelectionButtonsEnabled(false);
        greenTaskTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> setSelectionButtonsEnabled(newVal != null)
        );
    }

    public void handleAdd()
    {
        ViewManager.showView(Views.ADD_GREEN_TASK);
    }

    public void handleViewDetails()
    {

        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.VIEW_SINGLE_GREEN_TASK, String.valueOf(selected.getId()));
    }

    public void handleDelete()
    {

    public void handleUpdate()
    {
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

            showConfirmation("Opgaven \"" + selected.getName() + "\" er slettet.");
        }
    }

    public void handleSearch()
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


    public void handleClearSearchBar()
    {
        searchTxtField.clear();
        greenTaskTable.setItems(allGreenTasks);
    }

    public void handleRegister()
    {
        Task selectedTask = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) return;

        ViewManager.showView(Views.REGISTER_GREEN_TASK, selectedTask.getId() + "");
    }

    @Override
    public void setFlashMessage(String message)
    {
        UtilityMethods.showFlashMessage(message);
    }
}

    private void loadGreenTasks()
    {
        allGreenTasks.setAll(dataManager.getAllGreenTasks());
        greenTaskTable.setItems(allGreenTasks);
    }

    private void setSelectionButtonsEnabled(boolean enabled)
    {
        setButtonState(viewDetailsButton, enabled);
        setButtonState(registerButton, enabled);
        setButtonState(updateButton, enabled);
        setButtonState(deleteButton, enabled);
    }

    private void setButtonState(Button button, boolean enabled)
    {
        if (button == null) return;
        button.setDisable(!enabled);
        button.setOpacity(enabled ? 1.0 : 0.5);
    }

    private void showConfirmation(String message)
    {
        if (message == null || message.isBlank()) return;

        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
        alert.setTitle("Bekræftelse");
        alert.setHeaderText(null);
        alert.show();
    }
}
