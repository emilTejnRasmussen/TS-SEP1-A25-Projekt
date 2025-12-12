package kloeverly.presentation.controllers.green_task;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kloeverly.domain.GreenTask;
import kloeverly.domain.Task;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.AcceptsStringArgument;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class ViewGreenTaskController implements InitializableController, AcceptsStringArgument
{
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

    @FXML
    private Label errorLabel;

    @Override
    public void init(DataManager dataManager)
    {
        this.dataManager = dataManager;
        errorLabel.setText("");
        loadTaskIfReady();
    }

    @Override
    public void setArgument(String argument)
    {
        errorLabel.setText("");

        try
        {
            this.greenTaskId = Integer.parseInt(argument);
        }
        catch (NumberFormatException e)
        {
            this.greenTaskId = null;
        }

        loadTaskIfReady();
    }

    private void loadTaskIfReady()
    {
        if (dataManager == null || greenTaskId == null)
        {
            return;
        }

        Task task = dataManager.getTaskById(greenTaskId);
        if (!(task instanceof GreenTask))
        {
            errorLabel.setText("Kunne ikke finde grøn opgave.");
            return;
        }

        GreenTask greenTask = (GreenTask) task;

        idValueLabel.setText(String.valueOf(greenTask.getId()));
        nameValueLabel.setText(greenTask.getName());
        descriptionValueLabel.setText(greenTask.getDescription());
        pointsValueLabel.setText(String.valueOf(greenTask.getValue()));
    }

    @FXML
    private void handleUpdate()
    {
        errorLabel.setText("");

        if (greenTaskId == null)
        {
            errorLabel.setText("Kunne ikke finde ID på opgaven.");
            return;
        }

        ViewManager.showView(Views.UPDATE_GREEN_TASK, String.valueOf(greenTaskId));
    }

    @FXML
    private void handleGoBack()
    {
        ViewManager.showView(Views.GREEN_TASKS);
    }
}
