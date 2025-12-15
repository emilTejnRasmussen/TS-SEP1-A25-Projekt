package kloeverly.presentation.controllers.green_task;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

import java.util.Optional;

public class ViewAllGreenTasksController implements InitializableController
{
    @FXML public TextField searchTxtField;

    @FXML public TableView<GreenTask> greenTaskTable;
    @FXML public TableColumn<GreenTask, String> nameColumn;
    @FXML public TableColumn<GreenTask, String> descriptionColumn;
    @FXML public TableColumn<GreenTask, Number> pointsColumn;

    @FXML public Button addButton;
    @FXML public Button viewDetailsButton;
    @FXML public Button registerButton;
    @FXML public Button updateButton;
    @FXML public Button deleteButton;

    private DataManager dataManager;

    private final ObservableList<GreenTask> allGreenTasks =
            FXCollections.observableArrayList();

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;

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

    public void handleRegister()
    {
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.REGISTER_GREEN_TASK, String.valueOf(selected.getId()));
    }

    public void handleUpdate()
    {
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ViewManager.showView(Views.UPDATE_GREEN_TASK, String.valueOf(selected.getId()));
    }

    public void handleDelete()
    {
        GreenTask selected = greenTaskTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        ButtonType deleteType = new ButtonType("Slet opgave", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelType = new ButtonType("Annuller", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bekræft sletning");
        confirm.setHeaderText("Slet grøn opgave");
        confirm.setContentText("Er du sikker på, at du vil slette: \"" + selected.getName() + "\"?");
        confirm.getButtonTypes().setAll(deleteType, cancelType);

        Optional<ButtonType> result = confirm.showAndWait();
        Platform.runLater(() ->
                confirm.getDialogPane().lookupButton(cancelType).requestFocus());

        if (result.isPresent() && result.get() == deleteType)
        {
            dataManager.deleteTask(selected);
            ViewManager.updateExternalView();
            loadGreenTasks();
            setSelectionButtonsEnabled(false);

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
