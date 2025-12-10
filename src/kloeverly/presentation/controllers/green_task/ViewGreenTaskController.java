package kloeverly.presentation.controllers.green_task;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewGreenTaskController implements InitializableController, AcceptsStringArgument {

    private DataManager dataManager;
    private Integer greenTaskId;

    @FXML
    private Label nameValueLabel;

    @FXML
    private Label idValueLabel;

    @FXML
    private Label descriptionValueLabel;

    @FXML
    private Label pointsValueLabel;

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

        GreenTask task = (GreenTask) dataManager.getTaskById(greenTaskId);
        if (task == null) return;

        idValueLabel.setText(String.valueOf(task.getId()));
        nameValueLabel.setText(task.getName());
        descriptionValueLabel.setText(task.getDescription());
        // value = point
        pointsValueLabel.setText(String.valueOf(task.getValue()));
    }

    @FXML
    private void handleBack() {
        ViewManager.showView(Views.GREEN_TASKS);
    }
}
