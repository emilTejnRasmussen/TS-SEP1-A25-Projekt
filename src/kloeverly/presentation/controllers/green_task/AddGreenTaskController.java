package kloeverly.presentation.controllers.green_task;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kloeverly.domain.GreenTask;
import kloeverly.persistence.DataManager;
import kloeverly.presentation.core.InitializableController;
import kloeverly.presentation.core.ViewManager;
import kloeverly.presentation.core.Views;

public class AddGreenTaskController implements InitializableController
{
    private DataManager dataManager;

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
    }

    @FXML
    private void handleAdd()
    {
        errorLabel.setText("");

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

        GreenTask task = new GreenTask(name, description, points);
        dataManager.addTask(task);

        ViewManager.updateExternalView();
        ViewManager.showView(Views.GREEN_TASKS);
    }

    @FXML
    private void handleCancel()
    {
        ViewManager.showView(Views.GREEN_TASKS);
    }
}
