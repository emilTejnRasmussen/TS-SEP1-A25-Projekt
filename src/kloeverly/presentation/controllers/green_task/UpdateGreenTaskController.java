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

public class UpdateGreenTaskController implements InitializableController, AcceptsStringArgument
{
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
            currentTask = null;
            return;
        }

        currentTask = (GreenTask) task;

        nameField.setText(currentTask.getName());
        descriptionField.setText(currentTask.getDescription());
        pointsField.setText(String.valueOf(currentTask.getValue()));
    }

    @FXML
    private void handleUpdate()
    {
        errorLabel.setText("");

        if (currentTask == null)
        {
            errorLabel.setText("Ingen opgave valgt.");
            return;
        }

        String name = nameField.getText() == null ? "" : nameField.getText().trim();
        String description = descriptionField.getText() == null ? "" : descriptionField.getText().trim();
        String pointsText = pointsField.getText() == null ? "" : pointsField.getText().trim();

        if (name.isEmpty())
        {
            errorLabel.setText("Navn må ikke være tomt.");
            return;
        }

        if (description.isEmpty())
        {
            errorLabel.setText("Beskrivelse må ikke være tom.");
            return;
        }

        if (pointsText.isEmpty())
        {
            errorLabel.setText("Point må ikke være tomme.");
            return;
        }

        int points;
        try
        {
            points = Integer.parseInt(pointsText);
        }
        catch (NumberFormatException e)
        {
            errorLabel.setText("Point skal være et helt tal, fx 10.");
            return;
        }

        if (points <= 0)
        {
            errorLabel.setText("Point skal være et positivt tal.");
            return;
        }

        currentTask.setName(name);
        currentTask.setDescription(description);
        currentTask.setValue(points);

        dataManager.updateTask(currentTask);

        ViewManager.updateExternalView();
        ViewManager.showView(Views.VIEW_SINGLE_GREEN_TASK, greenTaskId + "", currentTask.formatTaskUpdated());
    }

    @FXML
    private void handleGoBack()
    {
        ViewManager.showView(Views.GREEN_TASKS);
    }
}
