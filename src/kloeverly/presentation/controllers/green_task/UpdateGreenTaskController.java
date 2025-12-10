package kloeverly.presentation.controllers.green_task;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.domain.GreenTask;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class UpdateGreenTaskController implements InitializableController, AcceptsStringArgument {

    private DataManager dataManager;
    private Integer greenTaskId;
    private GreenTask currentTask;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField pointsField;

    @FXML
    private Label errorLabel;

    @Override
    public void init(DataManager dataManager) {
        this.dataManager = dataManager;
        loadTaskIfReady();
    }

    @Override
    public void setArgument(String argument) {
        try {
            this.greenTaskId = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            this.greenTaskId = null;
        }
        loadTaskIfReady();
    }

    private void loadTaskIfReady() {
        if (dataManager == null || greenTaskId == null) return;

        Task task = dataManager.getTaskById(greenTaskId);
        if (!(task instanceof GreenTask)) return;

        currentTask = (GreenTask) task;

        nameField.setText(currentTask.getName());
        descriptionField.setText(currentTask.getDescription());
        // value = point
        pointsField.setText(String.valueOf(currentTask.getValue()));
    }

    @FXML
    private void handleUpdate() {
        if (currentTask == null) return;

        String name = nameField.getText() == null ? "" : nameField.getText().trim();
        String description = descriptionField.getText() == null ? "" : descriptionField.getText().trim();
        String pointsText = pointsField.getText() == null ? "" : pointsField.getText().trim();

        if (name.isEmpty() || description.isEmpty() || pointsText.isEmpty()) {
            errorLabel.setText("Navn, beskrivelse og point skal udfyldes.");
            return;
        }

        int points;
        try {
            points = Integer.parseInt(pointsText);
        } catch (NumberFormatException e) {
            errorLabel.setText("Point skal være et helt tal, fx 10 eller 25.");
            return;
        }

        currentTask.setName(name);
        currentTask.setDescription(description);
        // value = point
        currentTask.setValue(points);

        dataManager.updateTask(currentTask);

        ViewManager.showView(Views.GREEN_TASKS);
    }

    @FXML
    private void handleGoBack() {
        ViewManager.showView(Views.GREEN_TASKS);
    }
}
