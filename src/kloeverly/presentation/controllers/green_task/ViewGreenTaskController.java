package kloeverly.presentation.controllers.green_task;

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
    public Label nameValueLabel;
    public Label idValueLabel;
    public Label descriptionValueLabel;
    public Label pointsValueLabel;
    public Label errorLabel;

    private DataManager dataManager;
    private Integer greenTaskId;

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
            greenTaskId = Integer.parseInt(argument);
        }
        catch (NumberFormatException e)
        {
            greenTaskId = null;
        }

        loadTaskIfReady();
    }

    public void handleUpdate()
    {
        errorLabel.setText("");

        if (greenTaskId == null)
        {
            errorLabel.setText("Kunne ikke finde ID på opgaven.");
            return;
        }

        ViewManager.showView(Views.UPDATE_GREEN_TASK, String.valueOf(greenTaskId));
    }

    public void handleGoBack()
    {
        ViewManager.showView(Views.GREEN_TASKS);
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
}
