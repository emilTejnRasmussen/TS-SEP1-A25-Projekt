package kloeverly.presentation.controllers.green_task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewAllGreenTasksController implements InitializableController
{
    public TextField searchTxtField;

    public TableView<GreenTask> greenTaskTable;
    public TableColumn<GreenTask, String> nameColumn;
    public TableColumn<GreenTask, String> descriptionColumn;
    public TableColumn<GreenTask, Number> pointsColumn;

    public Label errorLabel;

    public Button addButton;
    public Button viewDetailsButton;
    public Button registerButton;
    public Button updateButton;
    public Button deleteButton;

    private DataManager dataManager;

    private final ObservableList<GreenTask> allGreenTasks =
            FXCollections.observableArrayList();

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        clearError();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        loadGreenTasks();

        setSelectionButtonsEnabled(false);

        greenTaskTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> setSelectionButtonsEnabled(newVal != null)
        );
    }

    public void handleAdd()
    {
        clearError();
        ViewManager.showView(Views.ADD_GREEN_TASK);
    }

    public void handleViewDetails()
    {
        clearError();
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.VIEW_SINGLE_GREEN_TASK, String.valueOf(selected.getId()));
    }

    public void handleRegister()
    {
        clearError();
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.REGISTER_GREEN_TASK, String.valueOf(selected.getId()));
    }

    public void handleUpdate()
    {
        clearError();
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.UPDATE_GREEN_TASK, String.valueOf(selected.getId()));
    }

    public void handleDelete()
    {
        clearError();
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        dataManager.deleteTask(selected);
        ViewManager.updateExternalView();
        loadGreenTasks();
        setSelectionButtonsEnabled(false);
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
        clearError();
        searchTxtField.clear();
        greenTaskTable.setItems(allGreenTasks);
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

    private void clearError()
    {
        if (errorLabel != null)
        {
            errorLabel.setText("");
        }
    }
}
